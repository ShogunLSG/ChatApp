using Messenger.model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Messenger.Models
{
    public class UserInstance
    {
        private static UserInstance instance;
        private User user;

        public User GetUser()
        {
            return user;
        }

        public void SetUser(User user)
        {
            this.user = user;
        }

        public void Clear()
        {
            user = null;
        }

        private UserInstance() { }

        public static UserInstance GetInstance()
        {
            if (instance == null)
            {
                instance = new UserInstance();
            }
            return instance;
        }
    }
}
