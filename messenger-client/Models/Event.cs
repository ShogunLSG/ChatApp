using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Messenger.Models
{
    public class Event
    {
        public int id { get; set; }
        public string name { get; set; }
        public string expiryTime { get; set; }
    }
}
