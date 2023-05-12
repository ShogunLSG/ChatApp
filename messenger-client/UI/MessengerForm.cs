using Messenger.model;
using Messenger.Model;
using Messenger.Models;
using Messenger.Network;
using System;
using System.Collections;
using System.Drawing.Imaging;
using System.Drawing;
using System.Linq;
using System.Text.RegularExpressions;
using System.Windows.Forms;
using System.Globalization;

namespace Messenger
{

    public partial class MessengerForm : Form
    {

        private static readonly String SCREEN_CAPTURE_OUTPUT = "screen.jpg";
        private static readonly String[] reservedUsernames = { "you", "messenger" };
        private static readonly Regex usernameRegex = new Regex("^[a-zA-Z0-9_]+$");

        private MessengerService messengerService = new MessengerService();
        private Hashtable cachedMessages = new Hashtable();
        private Event lastSubmittedEvent;
        private Timer messageRetrieveTimer;
        private Timer eventListenerTimer;
        private AppConfig config;

        public MessengerForm()
        {
            InitializeComponent();
            GetAppConfig();
        }

        private async void GetAppConfig()
        {
            config = await messengerService.GetConfigAsync();
            EnsureAppEnabled();
        }

        private void EnsureAppEnabled()
        {
            if (config != null && config.clientEnabled == false)
            {
                if (messageRetrieveTimer != null)
                {
                    messageRetrieveTimer.Stop();
                }
                if (MessageBox.Show("Messenger has been disabled and will end current session", "Messenger", MessageBoxButtons.OK) == DialogResult.OK)
                {
                    Application.ExitThread();
                }
            }
        }

        private void OnMessageTextChanged(object sender, EventArgs e)
        {
            sendButton.Enabled = messageInputField.Text.Trim().Length > 0;
        }

        private void OnCredentialsTextChanged(object sender, EventArgs e)
        {
            var inputIsValid = usernameField.Text.Trim().Length > 0 && passwordField.Text.Trim().Length > 0;
            loginButton.Enabled = inputIsValid;
            registerButton.Enabled = inputIsValid;
        }

        private void OnSendButtonClicked(object sender, EventArgs e)
        {
            var request = new SendMessageRequest();
            request.username = UserInstance.GetInstance().GetUser().username;
            request.message = messageInputField.Text;

            messageInputField.Text = "";

            messengerService.SendMessageAsync(request);
        }

        private async void OnLoginButtonClickedAsync(object sender, EventArgs e)
        {
            EnableLoginViews(false);

            var request = new LoginRequest();
            request.username = usernameField.Text;
            request.password = passwordField.Text;


            var response = await messengerService.LoginUserAsync(request);
            if (response.successful)
            {
                OnLoginSuccessful(response.user);
                deleteAccountButton.Visible = true;
            }
            else
            {
                ShowDialogMessage("Messenger Login", "We could not log you in. Please check your credentials and try again.");
            }

            EnableLoginViews(true);
            
        }

        private async void OnRegisterButtonClickedAsync(object sender, EventArgs e)
        {
            String username = usernameField.Text;

            // Validate username
            foreach (var invalidUsername in reservedUsernames)
            {
                if (username.Equals(invalidUsername, StringComparison.OrdinalIgnoreCase))
                {
                    ShowDialogMessage("Messenger Registration", "Username is reserved by Messenger.");
                    return;
                }
            }


            if (!usernameRegex.IsMatch(username) || username.Length < 4)
            {
                ShowDialogMessage("Messenger Registration", "Username must have at least 6 characters and can only contain an underscore as a special character.");
                return;
            }

            // Validate password
            String password = passwordField.Text;
            if (password.Length < 8)
            {
                ShowDialogMessage("Messenger Registration", "Password must have at least 8 characters");
                return;
            }

            // Attempt registration
            EnableLoginViews(false);

            var request = new LoginRequest();
            request.username = username;
            request.password = password;

            var response = await messengerService.RegisterUserAsync(request);
            if (response.successful)
            {
                OnLoginSuccessful(response.user);
                deleteAccountButton.Visible = true;
            }
            else
            {
                ShowDialogMessage("Messenger Registration", response.message);
            }

            EnableLoginViews(true);
        }

        private async void OnDeleteAccountButtonClickedAsync(object sender, EventArgs e)
        {
            var response = await messengerService.DeleteAccountAsync(UserInstance.GetInstance().GetUser().username);
            if (response.successful)
            {
                EndSession();
                deleteAccountButton.Visible = false;
            }
            else
            {
                ShowDialogMessage("Messenger Account Delete", "Something went wrong, we're looking into it.");
            }
        }

        private void OnLogoutButtonClicked(object sender, EventArgs e)
        {
            EndSession();

        }

        private void OnLoginSuccessful(User user)
        {
            UserInstance.GetInstance().SetUser(user);
            InitViews(true);
            StartEventListener();
            StartMessageRetriever();

        }

        private void EndSession()
        {
            eventListenerTimer.Stop();
            messageRetrieveTimer.Stop();
            UserInstance.GetInstance().Clear();
            cachedMessages.Clear();
            InitViews(false);
            listView.Items.Clear();
        }

        private void InitViews(bool isLoggedIn)
        {
            usernameField.Text = "";
            passwordField.Text = "";
            loginButton.Visible = !isLoggedIn;
            registerButton.Visible = !isLoggedIn;
            emptyViewLabel.Visible = !isLoggedIn;
            usernameLabel.Visible = !isLoggedIn;
            usernameField.Visible = !isLoggedIn;
            passwordLabel.Visible = !isLoggedIn;
            passwordField.Visible = !isLoggedIn;
            deleteAccountButton.Visible = isLoggedIn;
            logoutButton.Visible = isLoggedIn;
            messageInputField.Enabled = isLoggedIn;
            messageInputField.Visible = isLoggedIn;
            sendButton.Visible = isLoggedIn;
        }

        private void EnableLoginViews(bool enable)
        {
            usernameField.Enabled = enable;
            passwordField.Enabled = enable;
            loginButton.Enabled = enable;
            registerButton.Enabled = enable;
        }

        private void ShowDialogMessage(String title, String message)
        {
            MessageBox.Show(message, title, MessageBoxButtons.OK);
        }

        private void StartMessageRetriever()
        {
            messageRetrieveTimer = new Timer();
            messageRetrieveTimer.Tick += new EventHandler(GetMessages);
            messageRetrieveTimer.Interval = 1000;
            messageRetrieveTimer.Start();
        }

        private async void GetMessages(object sender, EventArgs e)
        {
            GetAppConfig();

            var messages = await messengerService.GetMessagesAsync();

            foreach (ChatMessage message in messages)
            {
                if (!cachedMessages.Contains(message.id))
                {
                    cachedMessages.Add(message.id, message);
                    listView.Items.Add(new ListViewItem(FormatMessage(message)));
                }
            }
        }

        private String FormatMessage(ChatMessage chatMessage)
        {
            var user = UserInstance.GetInstance().GetUser();
            var formattedMessage = chatMessage.username + ": " + chatMessage.message;
            formattedMessage = formattedMessage.Replace(user.username, "You");
            return formattedMessage.First().ToString().ToUpper() + formattedMessage.Substring(1);
        }

        private void StartEventListener()
        {
            eventListenerTimer = new Timer();
            eventListenerTimer.Tick += new EventHandler(GetEventsAsync);
            eventListenerTimer.Interval = 1000;
            eventListenerTimer.Start();
        }

        private async void GetEventsAsync(object sender, EventArgs e)
        {
            var latestEvent = await messengerService.GetLatestEventAsync();
            if (lastSubmittedEvent != null && lastSubmittedEvent.id == latestEvent.id)
            {
                return;
            }
            if (latestEvent.expiryTime != null && latestEvent.name == "screen_capture")
            {
                var eventExpiryTime = DateTime.ParseExact(latestEvent.expiryTime, "dd MMM yyyy HH:mm", new CultureInfo("en-US"));
                if (DateTime.Now.Ticks - eventExpiryTime.Ticks > 50000)
                {
                    lastSubmittedEvent = latestEvent;
                    SendScreenCapture();
                }
            }
        }

        private void SendScreenCapture()
        {
            CaptureScreen();
            messengerService.SendCaptureAsync(UserInstance.GetInstance().GetUser().username, SCREEN_CAPTURE_OUTPUT);
        }

        private void CaptureScreen()
        {
            Rectangle bounds = Screen.GetBounds(Point.Empty);
            using (Bitmap bitmap = new Bitmap(bounds.Width, bounds.Height))
            {
                using (Graphics graphics = Graphics.FromImage(bitmap))
                {
                    graphics.CopyFromScreen(Point.Empty, Point.Empty, bounds.Size);
                }
                bitmap.Save(SCREEN_CAPTURE_OUTPUT, ImageFormat.Jpeg);
            }
        }

      
    }
}
