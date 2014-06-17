using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Data.SqlClient;
using System.Data;
using PowerSNS;
using PowerSNS_Svr.UserInformation;

namespace PowerSNS_Svr.CommentManager
{
    public class Comment
    {
        private SqlConnection conn = null;
        private SqlCommand cmd = null;


        public Comment()
        {
            conn = new SqlConnection();
            //conn.ConnectionString = @"Data Source=.\SQLEXPRESS;AttachDbFilename=D:\Projects\MyWebservice\MyWebservice\App_Data\Memo.MDF ;Integrated Security=True;User Instance=True";
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

        //发表评论
        public string postComment(int did, string comcontent,string fuid)
        {
            String dayNow = System.DateTime.Now.ToString("yyyy-MM-dd");

            string strCmd = string.Format("insert into Comment (Did,comcontent,Fuid,SubDate) values ({0},'{1}','{2}','{3}')",did,comcontent,fuid,dayNow);

            cmd.CommandText = strCmd;

            cmd.ExecuteNonQuery();

            return "OK";
        }


        //查看评论
        public List<ComInfo> getCommentById(string did)
        {
            List<ComInfo> comlist = new List<ComInfo>();

            string strCmd = string.Format("select C.Cid, C.comcontent,C.SubDate,U.UserName,U.Photo_Addr from Comment C, UserInfo U where C.Fuid = U.UID and C.Did = {0}", Convert.ToInt32(did));

            cmd.CommandText = strCmd;

            SqlDataAdapter da = new SqlDataAdapter(cmd);
            cmd.ExecuteNonQuery();

            DataSet ds = new DataSet();

            da.Fill(ds);

            int len = ds.Tables[0].Rows.Count;
            for (int i = 0; i < len; i++)
            {
                DataRow dr = ds.Tables[0].Rows[i];
                int _cid = Convert.ToInt32(dr[0].ToString());
                int _did = Convert.ToInt32(did);
                string _comment = dr[1].ToString();
                string _date = Convert.ToDateTime(dr[2]).ToString("d");
                string _fname = dr[3].ToString();
                string _addr = dr[4].ToString();
                ComInfo ci = new ComInfo(_cid, _did, _date, _comment, _fname,_addr);
                comlist.Add(ci);
            }

            return comlist;
        }

    }
}