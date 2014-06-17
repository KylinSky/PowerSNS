using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace PowerSNS_Svr.CommentManager
{
    public class ComInfo
    {
        public int Cid { set; get; }
        public int Did { set; get; }
        public string CDate { set; get; }
        public string ComContent { set; get; }
        public string FName { set; get; }
        public string Photo_Addr { set; get; }

        public ComInfo()
        {
        }

        public ComInfo(int _cid, int _did,string _date, string _comcontent,string _fname, string _photo)
        {
            Cid = _cid;
            Did = _did;
            CDate = _date;
            ComContent = _comcontent;
            FName = _fname;
            Photo_Addr = _photo;
        }

           

    }
}