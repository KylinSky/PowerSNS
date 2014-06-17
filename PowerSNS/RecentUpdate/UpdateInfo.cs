using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace PowerSNS_Svr.RecentUpdate
{
    public class UpdateInfo
    {
        public string fuid { set; get; }
        public string fname { set; get; }
        public int did { set; get; }
        public string title { set; get; }
        public string date { set; get; }
        public string photo { set; get; }


        public UpdateInfo()
        {
        }

        public UpdateInfo(string _fuid, string _fname, int _did, string _title, string _date, string _photo)
        {
            fuid = _fuid;
            fname = _fname;
            did = _did;
            title = _title;
            date = _date;
            photo = _photo;
        }


    }
}