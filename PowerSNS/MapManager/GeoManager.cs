using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;
using System.Data.OleDb;
using System.Data.SqlClient;
using System.Data;
using PowerSNS_Svr;
using PowerSNS;
using PowerSNS_Svr.UserInformation;

namespace GeoManager
{
    public class GeoInfo
    {
        public string uid;
        public string latitude;
        public string longitude;
        public string time;
    }

    public class UserInfo
    {
        public string id;
        public string uid;
        public string userName;
        public string mood;
        public int age;
        public string Gender;
        public string Photo_Addr;
    }

    public class GeoDAO
    {

        //E:\group5\test\App_Data\Database1
        //C:\Users\user\documents\visual studio 2010\Projects\WebService1\WebService1\App_Data\Database1
        //private static string db = @"Data Source=.\SQLEXPRESS;AttachDbFilename=D:\Projects\MyWebservice\MyWebservice\App_Data\Memo.MDF ;Integrated Security=True;User Instance=True";
        private static string db = MemoDatabaseAccess.connString;

      

        public int addGeoInfo(string uid, string latitude, string logitude)
        {
            string time = DateTime.Now.ToString();
            string sql = "insert into GeoInfo(uid,latitude,longitude,time) values('" + uid + "'," + latitude + "," + logitude + ",'" + time + "')";
            string sql2 = "delete from CurrentLoc where uid='" + uid + "'";
            string sql3 = "insert into CurrentLoc(uid,latitude,longitude,time) values('" + uid + "'," + latitude + "," + logitude + ",'" + time + "')";
            SqlConnection conn = new SqlConnection(db);
            SqlCommand cmd = new SqlCommand(sql, conn);
            SqlCommand cmd2 = new SqlCommand(sql2, conn);
            SqlCommand cmd3 = new SqlCommand(sql3, conn);
            conn.Open();
             cmd.ExecuteNonQuery();
             int affected = cmd2.ExecuteNonQuery();
            affected = cmd3.ExecuteNonQuery();
            conn.Close();
            return affected;
        }

        public List<GeoInfo> getInfoById(string uid)
        {
            List<GeoInfo> list = new List<GeoInfo>();
            GeoInfo myInfo;
            string sql = "select * from GeoInfo where uid='" + uid + "'";
            SqlConnection conn = new SqlConnection(db);
            SqlCommand cmd = new SqlCommand(sql, conn);
            SqlDataAdapter da = new SqlDataAdapter(cmd);
            DataSet ds = new DataSet();

            try
            {
                conn.Open();
                da.Fill(ds);
                conn.Close();
            }
            catch (Exception e)
            {
                return null;
            }

            for (int i = 0; i < ds.Tables[0].Rows.Count; i++)
            {
                myInfo = new GeoInfo();
                myInfo.latitude = ds.Tables[0].Rows[i][2].ToString();
                myInfo.longitude = ds.Tables[0].Rows[i][3].ToString();
                myInfo.time = ds.Tables[0].Rows[i][4].ToString();
                myInfo.uid = uid;

                list.Add(myInfo);
            }
            return list;
        }

        public List<UserInfo> getfriendInfoByCoordinate(string uid, double latitude, double longitude)
        {
            List<UserInfo> list = new List<UserInfo>();
            UserInfo myInfo;
            double currentLatitude = latitude;
            double currentLongitude =longitude;
            double maxLatitude = currentLatitude + 5;
            double minLatitude = currentLatitude - 5;
            double maxLongitude = currentLongitude + 5;
            double minLongitude = currentLongitude - 5;

           

            string sql = "select * from UserInfo as U, CurrentLoc as G where latitude>" + minLatitude + " and latitude<"
                + maxLatitude + " and longitude>" + minLongitude + " and longitude<" + maxLongitude  +
                " and U.uid=G.uid and U.uid in (select UID2 from FriendList where UID1='" + uid +"')";
            SqlConnection conn = new SqlConnection(db);
            SqlCommand cmd = new SqlCommand(sql, conn);
            SqlDataAdapter da = new SqlDataAdapter(cmd);
            DataSet ds = new DataSet();

            try
            {
                conn.Open();
                da.Fill(ds);
                conn.Close();
            }
            catch (Exception e)
            {
                return null;
            }

            for (int i = 0; i < ds.Tables[0].Rows.Count; i++)
            {
                myInfo = new UserInfo();
                //myInfo.id = ds.Tables[0].Rows[i][0].ToString();
                myInfo.uid = ds.Tables[0].Rows[i][0].ToString();
                myInfo.userName = ds.Tables[0].Rows[i][1].ToString();
                myInfo.mood = ds.Tables[0].Rows[i][3].ToString();
                if (!ds.Tables[0].Rows[i][4].ToString().Equals(""))
                    myInfo.age = Convert.ToInt32(ds.Tables[0].Rows[i][4].ToString());
                else
                    myInfo.age = 0;
                myInfo.Gender = ds.Tables[0].Rows[i][5].ToString();
                myInfo.Photo_Addr = ds.Tables[0].Rows[i][6].ToString();

                list.Add(myInfo);
            } 
            return list;
        }

        public List<GeoInfo> getFriendLocation(string uid)
        {
            List<GeoInfo> list = new List<GeoInfo>();
            GeoInfo myInfo;
            string sql = "select * from CurrentLoc,FriendList where uid=UID2 and UID1='" + uid + "'";
            SqlConnection conn = new SqlConnection(db);
            SqlCommand cmd = new SqlCommand(sql, conn);
            SqlDataAdapter da = new SqlDataAdapter(cmd);
            DataSet ds = new DataSet();

            try
            {
                conn.Open();
                da.Fill(ds);
                conn.Close();
            }
            catch (Exception e)
            {
                return null;
            }

            for (int i = 0; i < ds.Tables[0].Rows.Count; i++)
            {
                myInfo = new GeoInfo();
                myInfo.latitude = ds.Tables[0].Rows[i][2].ToString();
                myInfo.longitude = ds.Tables[0].Rows[i][3].ToString();
                myInfo.time = ds.Tables[0].Rows[i][4].ToString();
                myInfo.uid = uid;

                list.Add(myInfo);
            }
            return list;
        }

    };
}