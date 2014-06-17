using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace PowerSNS_Svr.PhotoManagement.View
{
    public class PhotoView
    {
        public string _id { get; set; }
        public string album_id { get; set; }
        public string name { get; set; }
        public string network_addr { get; set; }
        public string descript { get; set; }
        public string file_path { get; set; }
    }
}