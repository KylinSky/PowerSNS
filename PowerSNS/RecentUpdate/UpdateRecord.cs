using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace PowerSNS_Svr.RecentUpdate
{
    public class UpdateRecord
    {
        public string fuid { set; get; }
        public string fname { set; get; }
        public string ffuid { set; get; }
        public string ffname { set; get; }
        public string updatetype { set; get; }
        public int did { set; get; }
        public string date { set; get; }
        public string title { set; get; }
        public string photo { set; get; }


        public UpdateRecord()
        {
        }

        public UpdateRecord(string _fuid, string _fname, string _ffuid, string _ffname, string _updatetype, int _did, string _title, string _date, string _photo)
        {
            ffuid = _ffuid;
            ffname = _ffname;
            fuid = _fuid;
            fname = _fname;
            did = _did;
            title = _title;
            date = _date;
            photo = _photo;
            updatetype = _updatetype;
        }
    }
}