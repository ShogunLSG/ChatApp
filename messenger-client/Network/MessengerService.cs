using Messenger.Model;
using Messenger.Models;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.IO;
using System.Net.Http;
using System.Threading.Tasks;
using static System.Windows.Forms.VisualStyles.VisualStyleElement.StartPanel;

namespace Messenger.Network
{
    public class MessengerService
    {
        private static readonly String BASE_URL = "https://messengerchat.co.za:7000/api/v1/";

        private HttpClientProvider clientProvider = new HttpClientProvider();

        public async Task<AppConfig> GetConfigAsync()
        {
            var request = new HttpRequestMessage(HttpMethod.Get, BASE_URL + "config");

            var response = await clientProvider.getClient().SendAsync(request);
            response.EnsureSuccessStatusCode();

            String config = await response.Content.ReadAsStringAsync();
            return JsonConvert.DeserializeObject<AppConfig>(config);
        }

        public async Task<List<ChatMessage>> GetMessagesAsync()
        {
            var request = new HttpRequestMessage(HttpMethod.Get, BASE_URL + "message");

            var response = await clientProvider.getClient().SendAsync(request);
            response.EnsureSuccessStatusCode();

            String messages = await response.Content.ReadAsStringAsync();
            return JsonConvert.DeserializeObject<List<ChatMessage>>(messages);
        }

        public async void SendMessageAsync(SendMessageRequest sendMessageRequest)
        {
            var request = new HttpRequestMessage(HttpMethod.Post, BASE_URL + "message");
            request.Content = new StringContent(JsonConvert.SerializeObject(sendMessageRequest), null, "application/json");

            var response = await clientProvider.getClient().SendAsync(request);
            response.EnsureSuccessStatusCode();
        }

        private async Task<LoginResponse> LoginOrRegisterUserAsync(LoginRequest loginRequest, String action)
        {
            var request = new HttpRequestMessage(HttpMethod.Post, BASE_URL + "user/" + action);
            request.Content = new StringContent(JsonConvert.SerializeObject(loginRequest), null, "application/json");

            var response = await clientProvider.getClient().SendAsync(request);
            response.EnsureSuccessStatusCode();

            String loginResponse = await response.Content.ReadAsStringAsync();

            return JsonConvert.DeserializeObject<LoginResponse>(loginResponse);
        }

        public Task<LoginResponse> LoginUserAsync(LoginRequest loginRequest)
        {
            return LoginOrRegisterUserAsync(loginRequest, "login");
        }

        public Task<LoginResponse> RegisterUserAsync(LoginRequest loginRequest)
        {
            return LoginOrRegisterUserAsync(loginRequest, "register");
        }

        public async Task<DeleteAccountResponse> DeleteAccountAsync(String username)
        {
            var request = new HttpRequestMessage(HttpMethod.Delete, BASE_URL + "user/" + username);
            var response = await clientProvider.getClient().SendAsync(request);
            response.EnsureSuccessStatusCode();

            String deleteAccountResponse = await response.Content.ReadAsStringAsync();
            return JsonConvert.DeserializeObject<DeleteAccountResponse>(deleteAccountResponse);
        }

        public async Task<Event> GetLatestEventAsync()
        {
            var request = new HttpRequestMessage(HttpMethod.Get, BASE_URL + "event");
            var response = await clientProvider.getClient().SendAsync(request);
            response.EnsureSuccessStatusCode();

            String deleteAccountResponse = await response.Content.ReadAsStringAsync();
            return JsonConvert.DeserializeObject<Event>(deleteAccountResponse);
        }

        public async void SendCaptureAsync(String username, String filePath)
        {

            var content = new MultipartFormDataContent
            {
                { new StreamContent(File.OpenRead(filePath)), "attachment", filePath }
            };

            var request = new HttpRequestMessage(HttpMethod.Post, BASE_URL + "/event/" + username + "/capture");
            request.Content = content;

            var response = await clientProvider.getClient().SendAsync(request);
            //response.EnsureSuccessStatusCode();
        }
    }
}