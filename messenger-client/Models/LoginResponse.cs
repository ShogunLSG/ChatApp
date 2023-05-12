using Messenger.model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Messenger.Models
{
    public class LoginResponse
    {
        public Boolean successful { get; set; }
        public String message { get; set; }
        public User user { get; set; }
    }
}
