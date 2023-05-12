using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Messenger.Model
{
    public class ChatMessage
    {
        public String id { get; set; }
        public String username { get; set; }
        public String message { get; set; }
        public String timestamp { get; set; }
    }
}
