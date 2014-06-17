using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace PowerSNS_Svr.PhotoManagement.View
{
    public class EventView
    {
        public string event_id { get; set; }
        public string photo_id { get; set; }
        public string user_id { get; set; }
        public string album_id { get; set; }
        public string album_name { get; set; }
        public string user_name { get; set; }
        public string photo_name { get; set; }
        public string photo_network_adddr { get; set; }
    }
}