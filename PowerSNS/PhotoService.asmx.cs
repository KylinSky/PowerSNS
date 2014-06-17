using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;
using PowerSNS_Svr.PhotoManagement.FileAccess;
using System.IO;
using PowerSNS_Svr.PhotoManagement.DataAccess;
using PowerSNS_Svr.PhotoManagement.View;
using PowerSNS_Svr.PhotoManagement.Enum;


namespace PowerSNS_Svr
{
    /// <summary>
    /// Summary description for PhotoService
    /// </summary>
    [WebService(Namespace = "http://tempuri.org/")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    // To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
    // [System.Web.Script.Services.ScriptService]
    public class PhotoService : System.Web.Services.WebService
    {

        [WebMethod]
        public string HelloWorld()
        {
            return "Hello World";
        }


        [WebMethod(Description = "Web 服务提供的方法，图片列表。")]
        public List<PhotoView> getPhotoList(string album_id)
        {
            PhotoDatabaseAccess pda = new PhotoDatabaseAccess();
            pda.Open();
            List<PhotoView> pl = pda.getPhotosByAlbumId(album_id);
            pda.Close();
            return pl;
        }

        [WebMethod(Description = "Web 服务提供的方法，相册列表。")]
        public List<AlbumView> getAlbumList(string userid)
        {
            PhotoDatabaseAccess pda = new PhotoDatabaseAccess();
            pda.Open();
            List<AlbumView>al = pda.getAlbumList(userid);
            pda.Close();
            return al;
        }

        [WebMethod(Description = "Web 服务提供的方法，创建相册。")]
        public String CreateAlbum(string user_id, string album_name, string descript)
        {
            PhotoDatabaseAccess pda = new PhotoDatabaseAccess();
            PhotoFileAccess pfa = new PhotoFileAccess();
            string f_albumName = System.Guid.NewGuid().ToString() ;
            pda.Open();

            string album_file_path = pfa.CreateAlbum(f_albumName);

            if (pda.CreateAlbum(
                    user_id,
                    album_name,
                    descript,
                    f_albumName + "\\",
                    f_albumName + "/",
                    DefaultValue.DEFAULT_ALBUM_FACE_ADDR
                    ) == 1)
            {
                pda.Close();
                return "SUCCESS..";
            }
            else
            {
                pda.Close();
                return "WARNING:CREATE ALBUM FAILURE..";
            }
        }

        [WebMethod(Description = "Web 服务提供的方法，上传。")]
        public string UploadPhotoToAlbum(string userid, string albumid, string FileName, string descript, byte[] fs)
        {

            PhotoDatabaseAccess pda = new PhotoDatabaseAccess();
            pda.Open();

            string albumFilePath = pda.getAlbumFilePathByAlbum_id(albumid);
            string albumNetworkAddr = pda.getAlbumNetworkAddrByAlbum_id(albumid);

            if (albumFilePath == null || albumFilePath.Equals(""))
            {
                pda.Close();
                return "SERVICE ERROR:ALBUMS NOT EXIST..";
            }
            string f_photoNme = System.Guid.NewGuid().ToString() + FileName.Substring(FileName.LastIndexOf("."));

            PhotoFileAccess pfa = new PhotoFileAccess();

            string savedFilePath = pfa.CreateFile(albumFilePath, f_photoNme, fs);

            if (savedFilePath == null || savedFilePath.Equals(""))
            {
                pda.Close();
                return "SERVICE ERROR:FILE SAVE FAILURE...";
            }

            pda.CreatePhoto(albumid, FileName, descript, savedFilePath, albumNetworkAddr + f_photoNme);

            pda.Close();
            return "SUCCESS..";
        }


        [WebMethod(Description = "Web 服务提供的方法，设置封面。")]
        public String SetAlbumFace(string user_id, string album_id, string face_addr)
        {
            PhotoDatabaseAccess pda = new PhotoDatabaseAccess();
            pda.Open();
            int res = pda.SetAlbumFace(album_id, face_addr);
            pda.Close();
            if (res == 1)
            {
                pda.Close();
                return "SUCCESS..";
            }
            else
            {
                pda.Close();
                return "SERVICE EOORO:SET ALBUM FACE FAILURE..";
            }
        }

        [WebMethod(Description = "Web 服务提供的方法，删除相片。")]
        public String DeletePhoto(String photo_id)
        {
            PhotoDatabaseAccess pda = new PhotoDatabaseAccess();
            pda.Open();

            string delfile = pda.getPhotoFilePathByPhoto_id(photo_id);

            if (null == delfile || delfile.Equals(""))
            {
                pda.Close();
                return "SERVICE WARNING:NO SUCH PHOTO..";
            }

            PhotoFileAccess pfa = new PhotoFileAccess();

            pfa.DeletePhoto(delfile);

            if (pda.delectPhotoByPhotoId(photo_id) >= 1)
            {
                pda.Close();
                return "SUCCESS..";
            }
            else
            {
                pda.Close();
                return "SERVICE ERROR:DELETE PHOTO_TABLE FAILURE..";
                //return "SUCCESS..";
            }
        }

        [WebMethod(Description = "Web 服务提供的方法，删除相册。")]
        public String DeleteAlbum(String album_id)
        {
            PhotoDatabaseAccess pda = new PhotoDatabaseAccess();
            pda.Open();

            string delfile = pda.getAlbumFilePathByAlbum_id(album_id);

            if (null == delfile || delfile.Equals(""))
            {
                pda.Close();
                return "SERVICE WARNING:NO SUCH ALBUM..";
            }

            PhotoFileAccess pfa = new PhotoFileAccess();

            pfa.DeleteFolder(delfile);

            if (pda.delectAlbumByAlbumId(album_id) >= 1)
            {
                pda.Close();
                return "SUCCESS..";
            }
            else
            {
                pda.Close();
                return "SERVICE ERROR:DELETE ALBUM_TABLE FAILURE..";
            }
        }

        [WebMethod(Description = "Web 服务提供的方法，返回好友最近相册更新。")]
        public List<EventView> getFriendsEvents(string user_id)
        {
            PhotoDatabaseAccess pda = new PhotoDatabaseAccess();
            pda.Open();
            List<EventView> evlist = pda.getFriendsEnevts(user_id);
            pda.Close();
            return evlist;
        }
        



        [WebMethod(Description = "Web 服务提供的方法，返回是否文件上载成功与否。")]
        public string UploadFile(byte[] fs, string FileName)
        {
            try
            {
                ///定义并实例化一个内存流，以存放提交上来的字节数组。
                MemoryStream m = new MemoryStream(fs);
                ///定义实际文件对象，保存上载的文件。
                FileStream f = new FileStream("E:\\hahahaha\\"
                 + FileName, FileMode.Create);
                ///把内内存里的数据写入物理文件
                m.WriteTo(f);
                m.Close();
                f.Close();
                f = null;
                m = null;
                return "文件已经上传成功。";
            }
            catch (Exception ex)
            {
                return ex.Message;
            }
        }


    }
}
