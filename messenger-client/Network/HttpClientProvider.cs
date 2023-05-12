using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;

namespace Messenger.Network
{
    public class HttpClientProvider
    {
        private HttpClient client;

        public HttpClient getClient()
        {
            if (client == null)
            {
                client = new HttpClient();
                client.DefaultRequestHeaders.Accept.Add(new System.Net.Http.Headers.MediaTypeWithQualityHeaderValue("application/json"));
            }
            return client;
        }
    }
}
