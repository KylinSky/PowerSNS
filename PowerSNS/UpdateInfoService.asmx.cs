using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;
using PowerSNS_Svr.RecentUpdate;

namespace PowerSNS_Svr
{
    /// <summary>
    /// Summary description for UpdateInfoService
    /// </summary>
    [WebService(Namespace = "http://tempuri.org/")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    // To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
    // [System.Web.Script.Services.ScriptService]
    public class UpdateInfoService : System.Web.Services.WebService
    {

        [WebMethod]
        public List<UpdateInfo> GetUpdateInfo(string UID)
        {
            RecentDiaryUpdate rd = new RecentDiaryUpdate();
            rd.Open();
            List<UpdateInfo> lui = rd.getUpdateInfo(UID);
            rd.Close();
            return lui;
        }

        [WebMethod]
        public List<UpdateRecord> GetUpdateRecord(string UID)
        {
            RecentDiaryUpdate rd = new RecentDiaryUpdate();
            rd.Open();
            List<UpdateRecord> lur = rd.getUpdateRecord(UID);
            rd.Close();
            return lur;
        }

        [WebMethod]
        public string ShareDiary(string UID, int did)
        {
            RecentDiaryUpdate rd = new RecentDiaryUpdate();
            rd.Open();
            string ret = rd.shareDiary(UID, did);
            rd.Close();
            return ret;
        }
    }

 
}
