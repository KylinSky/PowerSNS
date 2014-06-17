using System;
using System.Collections;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Linq;
using System.Web;
using System.Web.Services;
using System.Web.Services.Protocols;
using System.Xml.Linq;
using System.Data.SqlClient;
using PowerSNS_Svr.UserInformation;
using PowerSNS_Svr.UserManager;
using PowerSNS_Svr.CommentManager;
using PowerSNS_Svr.PhotoManagement.Enum;


using System.IO;

namespace PowerSNS
{
    /// <summary>
    /// Service1 的摘要说明
    /// </summary>
    [WebService(Namespace = "http://tempuri.org/")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [ToolboxItem(false)]
    // 若要允许使用 ASP.NET AJAX 从脚本中调用此 Web 服务，请取消对下行的注释。
    // [System.Web.Script.Services.ScriptService]
    public class Service1 : System.Web.Services.WebService
    {

        private MemoData md;
        private Usermanager um;

        public Service1()
        {
            md = new MemoData();
            um = new Usermanager();
        }

        [WebMethod]
        public string HelloWorld()
        {

            SqlConnection conn = null;
            string connString = @" Data Source=.;Initial Catalog=Memo;User ID='sa';Password='sasa'";
            conn = new SqlConnection();
            conn.ConnectionString = connString;
            conn.Open();

            return "Hello World";
        }

        [WebMethod]
        public bool LoginCheck(string UID, string password)
        {
            return md.LoginCheck(UID, password);
        }

        [WebMethod]
        public UserInfo GetUserInfoById(string UID)
        {
            um.Open();
            UserInfo ui = um.GetUserInfoById(UID);
            um.Close();
            return ui;
        }


        [WebMethod]
        public string UpdateUserInfo(string UID, string _name, string _age, string _gender, string _mood)
        {
            um.Open();
            string ret = um.UpdateUserInfo(UID, _name, _age, _gender, _mood);
            um.Close();
            return ret;
        }

        [WebMethod]
        public bool RegisterNewAccount(string UID, string Password, string NickName)
        {
            return md.NewAccount(UID, Password, NickName);
        }

        [WebMethod]
        public string[] LoadMainPage(string UID)
        {
            return md.LoadMainPage(UID);
        }

        [WebMethod]
        public bool UpdateMood(string mood, string UID)
        {
            md.UpdateMood(mood, UID);
            return true;
        }

        [WebMethod]
        public bool WriteDiary(string title, string content, string UID)
        {
            return md.WriteDiary(UID, title, content);
        }

        [WebMethod]
        public string[] GetDiaryList(string UID)
        {
            return md.GetDiaryList(UID);
        }

        [WebMethod]
        public string[] ReadDiary(string did)
        {
            return md.ReadDiary(did);
        }

        [WebMethod]
        public string DeleteDiary(string did)
        {
            return md.DeleteDiary(did);
        }

        [WebMethod]
        public string UpdateDiary(string DContent, int did)
        {
            return md.UpdateDiary(DContent, did);
        }


        [WebMethod]
        public string FriendRequest(string UID1, string UID2)
        {
            return md.FriendRequest(UID1, UID2);
        }

        [WebMethod]
        public string AddFriend(string UID1, string UID2, string flag)
        {
            return md.addFriend(UID1, UID2, flag);
        }

        [WebMethod]
        public string DeleteFriend(string UID, string fUid)
        {
            return md.DeleteFriend(UID, fUid);
        }

        [WebMethod]
        public string[] GetFriendRequestList(string UID)
        {
            return md.getFriendRequestList(UID);
        }


        [WebMethod]
        public string[] GetFriendList(string UID)
        {
            return md.getFriendList(UID);
        }

        [WebMethod]
        public List<UserInfo> GetFriendInfo(string UID)
        {
            return md.GetFriendInfo(UID);
        }


        [WebMethod]
        public List<ComInfo> GetCommentList(string did)
        {
            Comment com = new Comment();
            com.Open();
            List<ComInfo> list = com.getCommentById(did);
            com.Close();

            return list;
        }


        [WebMethod]
        public string PostComment(int did, string comcontent, string fuid)
        {
            Comment com = new Comment();
            com.Open();
            com.postComment(did, comcontent, fuid);
            com.Close();
            return "OK";
        }

        [WebMethod]
        public string ChangePhoto(byte[] ImgIn, string FileName, string UID)
        {
            try
            {
                ///定义并实例化一个内存流，以存放提交上来的字节数组。
                MemoryStream m = new MemoryStream(ImgIn);

                string path = DefaultValue.WEB_SERVICE_ROOT + "//Photo_Face//" + UID + "//";
                if (!Directory.Exists(path))
                    Directory.CreateDirectory(path);

                string photo_addr = "Photo_Face/" + UID + "/" + FileName;
                um.Open();
                um.ChangePhoto(UID, photo_addr);
                um.Close();


                ///定义实际文件对象，保存上载的文件。
                FileStream f = new FileStream(path + FileName
                 , FileMode.Create);
                ///把内内存里的数据写入物理文件
                m.WriteTo(f);
                m.Close();
                f.Close();
                f = null;
                m = null;
                return "OK";
            }
            catch (IOException e)
            {
                Console.WriteLine(e.ToString());
                return "NO";
            }

        }
    }
}
