using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Data.SqlClient;
using System.Data;
using PowerSNS_Svr.PhotoManagement.FileAccess;
using PowerSNS_Svr.PhotoManagement.View;
using PowerSNS_Svr.PhotoManagement.Enum;
using PowerSNS;
using PowerSNS_Svr.UserInformation;


namespace PowerSNS_Svr.PhotoManagement.DataAccess
{

    public class PhotoDatabaseAccess
    {

        private SqlConnection conn = null;
        private SqlCommand cmd;

        public PhotoDatabaseAccess()
        {
            conn = new SqlConnection();
            //release
            //conn.ConnectionString = @"Data Source=.\SQLEXPRESS;AttachDbFilename=D:\Projects\MyWebservice\MyWebservice\App_Data\Memo.MDF ;Integrated Security=True;User Instance=True";
            conn.ConnectionString = MemoDatabaseAccess.connString;
            //conn.ConnectionString = @"Data Source=.\SQLEXPRESS;AttachDbFilename=E:\web\wwwroot_web_service\APP_DATA\DATABASE1.MDF ;Integrated Security=True;User Instance=True";
            //debug
            //conn.ConnectionString=@"Data Source=.\SQLEXPRESS;AttachDbFilename=C:\Users\user\Documents\Visual Studio 2010\Projects\WebServiceDemo\WebServiceDemo\App_Data\Database1.mdf;Integrated Security=True;User Instance=True";
            cmd = new SqlCommand();
            cmd.Connection = conn;
            cmd.CommandType = CommandType.Text;

        }

        public void Open() {
            conn.Open();
        }


        public List<PhotoView> getPhotosByAlbumId(string albumID)
        {
            List<PhotoView> plist = new List<PhotoView>();

            string SQLStatement = "SELECT * FROM photo AS p1 WHERE p1.album_id=" +albumID;

            // Create a SqlDataAdapter to get the results as DataTable
            SqlDataAdapter SQLDataAdapter = new SqlDataAdapter(SQLStatement, conn);

            // Create a new DataTable
            DataTable dtResult = new DataTable();

            // Fill the DataTable with the result of the SQL statement
            SQLDataAdapter.Fill(dtResult);

            foreach (DataRow drRow in dtResult.Rows)
            {
                PhotoView ph = new PhotoView();
                ph._id = drRow["_id"].ToString();
                ph.album_id = drRow["album_id"].ToString();
                ph.name = drRow["name"].ToString();
                ph.network_addr = drRow["network_addr"].ToString();
                ph.file_path = drRow["file_path"].ToString();
                ph.descript = drRow["descript"].ToString();
                plist.Add(ph);
            }
            return plist;
        }

        public string getAlbumNetworkAddrByAlbum_id(string album_id)
        {

            string path = "";

            string SQLStatement = "SELECT * FROM album WHERE _id='" + album_id + "\'";

            // Create a SqlDataAdapter to get the results as DataTable
            SqlDataAdapter SQLDataAdapter = new SqlDataAdapter(SQLStatement, conn);

            // Create a new DataTable
            DataTable dtResult = new DataTable();

            // Fill the DataTable with the result of the SQL statement
            SQLDataAdapter.Fill(dtResult);

            foreach (DataRow drRow in dtResult.Rows)
            {

                path = drRow["network_addr"].ToString();
            }
            return path;
        }

        public string getAlbumFilePathByAlbum_id(string album_id) {

            string path="";

            string SQLStatement = "SELECT * FROM album WHERE _id='" + album_id + "\'";

            // Create a SqlDataAdapter to get the results as DataTable
            SqlDataAdapter SQLDataAdapter = new SqlDataAdapter(SQLStatement, conn);

            // Create a new DataTable
            DataTable dtResult = new DataTable();

            // Fill the DataTable with the result of the SQL statement
            SQLDataAdapter.Fill(dtResult);

            foreach (DataRow drRow in dtResult.Rows)
            {
             
                path = drRow["file_path"].ToString();
            }
            return  path;
        }

        public List<AlbumView> getAlbumList(string userid)
        {
            List<AlbumView> alist = new List<AlbumView>();

            string SQLStatement = "SELECT * FROM album WHERE user_id='"+ userid +"\'" ;

            // Create a SqlDataAdapter to get the results as DataTable
            SqlDataAdapter SQLDataAdapter = new SqlDataAdapter(SQLStatement, conn);

            // Create a new DataTable
            DataTable dtResult = new DataTable();

            // Fill the DataTable with the result of the SQL statement
            SQLDataAdapter.Fill(dtResult);

            foreach (DataRow drRow in dtResult.Rows)
            {
                AlbumView al = new AlbumView();
                al._id = Convert.ToInt32(drRow["_id"].ToString());
                al.user_id = drRow["user_id"].ToString();
                al.name = drRow["name"].ToString();
                al.face_addr = drRow["face_addr"].ToString();
                al.size = Convert.ToInt32(drRow["size"].ToString());
                al.file_path = drRow["file_path"].ToString();
                al.network_addr = drRow["network_addr"].ToString();
                al.descript = drRow["descript"].ToString();
                alist.Add(al);
            }
            return alist;
        }

        public int CreateAlbum(string userid, string album_name,string descript,string file_path,string network_addr,string face_addr) {

            //PhotoDatabaseAccess pda = new PhotoDatabaseAccess();
            string strCmd = "insert into album (user_id,name,file_path,network_addr,face_addr,size,descript) values(\'"
                + userid 
                + "\',\'" + album_name 
                + "\',\'" + file_path 
                + "\',\'" + network_addr
                + "\',\'" + face_addr
                + "\',\'" +"0"
                + "\',\'" + descript + "\')";
            cmd.CommandText = strCmd;
            return cmd.ExecuteNonQuery() ;
        }

        public int CreatePhoto(string album_id, string photo_name,string descript,string file_path,string network_addr) {
           // PhotoDatabaseAccess pda = new PhotoDatabaseAccess();
            string strCmd = "insert into photo (album_id,name,descript,file_path,network_addr) values(\'"
                + album_id
                + "\',\'" + photo_name
                + "\',\'" + descript
                 + "\',\'" + file_path
                + "\',\'" + network_addr + "\')";
            cmd.CommandText = strCmd;
            return  cmd.ExecuteNonQuery();
        }

        public int SetAlbumFace(String album_id, String face_addr)
        {
          //  PhotoDatabaseAccess pda = new PhotoDatabaseAccess();
            string strCmd = "UPDATE album SET face_addr = '"+ face_addr+ "\'"+"WHERE _id='"+album_id+"\'";
            cmd.CommandText = strCmd;
            return cmd.ExecuteNonQuery();
        }

        public void Close(){
            conn.Close();
        }

        public int delectPhotoByPhotoId(string photo_id) {
            string strCmd = "DELETE FROM photo WHERE _id='"+photo_id+"\'";
            cmd.CommandText = strCmd;
            return cmd.ExecuteNonQuery();
        }

        public int delectAlbumByAlbumId(string album_id)
        {
            string strCmd = "DELETE FROM album WHERE _id='" + album_id + "\'";
            cmd.CommandText = strCmd;
            return cmd.ExecuteNonQuery();
        }

        public string getPhotoFilePathByPhoto_id(string photo_id)
        {
            string fpath = "";
            string SQLStatement = "SELECT * FROM photo WHERE _id='" + photo_id + "\'";
            // Create a SqlDataAdapter to get the results as DataTable
            SqlDataAdapter SQLDataAdapter = new SqlDataAdapter(SQLStatement, conn);

            // Create a new DataTable
            DataTable dtResult = new DataTable();

            // Fill the DataTable with the result of the SQL statement
            SQLDataAdapter.Fill(dtResult);

            foreach (DataRow drRow in dtResult.Rows)
            {

                fpath = drRow["file_path"].ToString();
            }
            return fpath;
        }


        public string getPhotoNetwork_addrByPhoto_id(string photo_id)
        {
            string npath = "";
            string SQLStatement = "SELECT network_addr FROM photo WHERE _id='" + photo_id + "\'";
            // Create a SqlDataAdapter to get the results as DataTable
            SqlDataAdapter SQLDataAdapter = new SqlDataAdapter(SQLStatement, conn);

            // Create a new DataTable
            DataTable dtResult = new DataTable();

            // Fill the DataTable with the result of the SQL statement
            SQLDataAdapter.Fill(dtResult);

            foreach (DataRow drRow in dtResult.Rows)
            {
                npath = drRow["network_addr"].ToString();
            }
            return npath;
        }

        public List<EventView> getFriendsEnevts(string user_id)
        {
            List<EventView> evlist = new List<EventView>();
            string SQLStatement =
                   " SELECT   newevent._id as event_id, album.user_id, photo.album_id, photo._id AS photo_id, photo.name AS photo_name, album.name AS album_name,userinfo.username AS user_name, photo.network_addr AS photo_network_addr FROM newevent INNER JOIN friendlist ON newevent.user_id = friendlist.UID2 INNER JOIN photo ON newevent.photo_id = photo._id INNER JOIN album ON photo.album_id = album._id INNER JOIN userinfo ON friendlist.UID2 = userinfo.UID WHERE (\'" +
                    user_id +
                    "\' = friendlist.UID1)order by newevent._id desc";
            // Create a SqlDataAdapter to get the results as DataTable
            SqlDataAdapter SQLDataAdapter = new SqlDataAdapter(SQLStatement, conn);
            // Create a new DataTable
            DataTable dtResult = new DataTable();

            // Fill the DataTable with the result of the SQL statement
            SQLDataAdapter.Fill(dtResult);

            foreach (DataRow drRow in dtResult.Rows)
            {
                EventView ev = new EventView();
                ev.event_id = drRow["event_id"].ToString();
                ev.user_id = drRow["user_id"].ToString();
                ev.album_id = drRow["album_id"].ToString();
                ev.photo_id = drRow["photo_id"].ToString();
                ev.photo_name = drRow["photo_name"].ToString();
                ev.album_name = drRow["album_name"].ToString();
                ev.user_name = drRow["user_name"].ToString();
                ev.photo_network_adddr = drRow["photo_network_addr"].ToString();
                evlist.Add(ev);
            }

            return evlist;
        }


    }
}
