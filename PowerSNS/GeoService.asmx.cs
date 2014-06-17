using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;
using GeoManager;


namespace PowerSNS_Svr
{
    /// <summary>
    /// Summary description for GeoService
    /// </summary>
    [WebService(Namespace = "http://tempuri.org/")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    // To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
    // [System.Web.Script.Services.ScriptService]
    public class GeoService : System.Web.Services.WebService
    {
        private GeoDAO  myManager = new GeoDAO();
        [WebMethod]
        public string HelloWorld()
        {
            return "Hello World";
        }

        [WebMethod]
        public int insertInfo(string uid, string latitude, string longitude)
        {
            return myManager.addGeoInfo(uid, latitude, longitude);
        }

        [WebMethod]
        public List<GeoInfo> getGeoList(string uid)
        {
            return myManager.getInfoById(uid);
        }

        [WebMethod]
        public List<UserInfo> getUserList(string uid, string latitude, string longitude)
        {
            return myManager.getfriendInfoByCoordinate(uid,Double.Parse(latitude), Double.Parse(longitude));
        }

        [WebMethod]
        public List<GeoInfo> getUserLocations(string uid)
        {
            return myManager.getFriendLocation(uid);
        }
    }
}
