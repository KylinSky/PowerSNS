using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Data;
using System.Data.SqlClient;
using PowerSNS;
using PowerSNS_Svr.UserInformation;

namespace PowerSNS_Svr.RecentUpdate
{
    public class RecentDiaryUpdate
    {
        private SqlConnection conn = null;
        private SqlCommand cmd = null;

        public RecentDiaryUpdate()
        {
            conn = new SqlConnection();
           // conn.ConnectionString = @"Data Source=.\SQLEXPRESS;AttachDbFilename=D:\Projects\MyWebservice\MyWebservice\App_Data\Memo.MDF ;Integrated Security=True;User Instance=True";
            conn.ConnectionString = MemoDatabaseAccess.connString;
        }


        public void Open()
        {
            conn.Open();
            cmd = new SqlCommand();
            cmd.Connection = conn;
            cmd.CommandType = CommandType.Text;
        }

        public void Close()
        {
            conn.Close();
        }

        public List<UpdateInfo> getUpdateInfo(string UID)
        {
            
            List<UpdateInfo> lui = new List<UpdateInfo>();

            string strCmd = string.Format("select U.UID,U.UserName,D.Did,D.Title,D.DDate,U.Photo_Addr from UserInfo U , FriendList F, Diary D where U.UID = F.UID2 and D.UID = F.UID2 and F.UID1 = '{0}' order by D.DDate desc",UID);
            cmd.CommandText = strCmd;
            SqlDataAdapter da = new SqlDataAdapter(cmd);
            cmd.ExecuteNonQuery();

            DataSet ds = new DataSet();
            da.Fill(ds);

            int len = ds.Tables[0].Rows.Count;
            len = (len < 10 ? len : 10);

            for (int i = 0; i < len; i++)
            {
                DataRow dr = ds.Tables[0].Rows[i];
                string fuid = dr[0].ToString();
                string fname = dr[1].ToString();
                int did = Convert.ToInt32(dr[2].ToString());
                string title = dr[3].ToString();
                string date = Convert.ToDateTime(dr[4]).ToString("d");
                string photo = dr[5].ToString();

                UpdateInfo ui = new UpdateInfo(fuid, fname, did, title, date, photo);
                lui.Add(ui);
               
            }

            return lui;
        }


        public List<UpdateRecord> getUpdateRecord(string UID)
        {

            List<UpdateRecord> lur = new List<UpdateRecord>();

            string strCmd = string.Format("SELECT   U1.UID AS fuid, U1.UserName AS fname, U1.Photo_Addr AS photo, U2.UID AS ffuid, U2.UserName AS ffname, U3.updatetype,U3.updatedate AS updatedate, D.DID AS did,  D.Title AS title FROM" 
                +     "  UserInfo AS U2 INNER JOIN "
                +  "  UserInfo AS U1 INNER JOIN" 
                + "  UpdateInfo AS U3 INNER JOIN" 
                +  " FriendList ON U3.UID = FriendList.UID2 INNER JOIN "
                +  " Diary AS D ON U3.item_id = D.DID ON U1.UID = FriendList.UID2 ON U2.UID = D.UID"
                +  "  WHERE  FriendList.UID1 = '{0}'",UID);
            cmd.CommandText = strCmd;
            SqlDataAdapter da = new SqlDataAdapter(cmd);
            cmd.ExecuteNonQuery();

            DataSet ds = new DataSet();
            da.Fill(ds);

            int len = ds.Tables[0].Rows.Count;
            len = (len < 10 ? len : 10);

            for (int i = 0; i < len; i++)
            {
                DataRow dr = ds.Tables[0].Rows[i];
                String fuid = dr["fuid"].ToString();
                String fname = dr["fname"].ToString();
                String ffuid = dr["ffuid"].ToString();
                String ffname = dr["ffname"].ToString();
                String updatetype = dr["updatetype"].ToString();
                String updatedate = Convert.ToDateTime(dr["updatedate"]).ToString("d");
                int did = Convert.ToInt32(dr["did"].ToString());
                String title = dr["title"].ToString();
                String photo = dr["photo"].ToString();

                UpdateRecord ur = new UpdateRecord(fuid,fname,ffuid,ffname,updatetype,did,title,updatedate,photo);
                lur.Add(ur);
            }

            return lur;
        }


        public string shareDiary(string UID, int did)
        {
            string ret = "OK";

            string dateNow = System.DateTime.Now.ToString("yyyy-MM-dd");

            string strCmd = string.Format("insert into UpdateInfo (UID,item_id,updatetype,updatedate) values ('{0}',{1},'share','{2}')",UID,did,dateNow);
            cmd.CommandText = strCmd;
            cmd.ExecuteNonQuery();

            return ret;
        }


    }
}