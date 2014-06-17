using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Data;
using System.Data.SqlClient;
using PowerSNS;
using PowerSNS_Svr.UserInformation;

namespace PowerSNS_Svr.UserManager
{
    public class Usermanager
    {

        private SqlConnection conn = null;
        private SqlCommand cmd = null;

        public Usermanager()
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

        public UserInfo GetUserInfoById(string UID)
        {
            string strCmd = string.Format("select * from UserInfo where UID = \'{0}\'", UID);
            cmd.CommandText = strCmd;

            SqlDataAdapter da = new SqlDataAdapter(cmd);
            cmd.ExecuteNonQuery();

            DataSet ds = new DataSet();
            da.Fill(ds);

            UserInfo userinfo = new UserInfo();

            DataRow dr = ds.Tables[0].Rows[0];
            userinfo.Id = dr["UID"].ToString();
            userinfo.Name = dr["username"].ToString();
            userinfo.Mood = dr["mood"].ToString();
            userinfo.Password = dr["password"].ToString();
            if (dr["Age"].ToString().Equals(""))
                userinfo.Age = 0;
            else
                userinfo.Age = Convert.ToInt32(dr["Age"].ToString());
            userinfo.Gender = dr["Gender"].ToString();
            userinfo.Photo_Addr = dr["Photo_Addr"].ToString();
            return userinfo;
        }


        public string UpdateUserInfo(string UID, string _name, string _age, string _gender, string _mood)
        {
            string ret = "T";

            string strCmd = string.Format("update UserInfo set UserName = '{0}' , Age ={1}, Gender = '{2}', mood= '{3}' where UID = '{4}'", _name, Convert.ToInt32(_age), _gender, _mood, UID);
            cmd.CommandText = strCmd;
            cmd.ExecuteNonQuery();

            return ret;
        }


        public string ChangePhoto(string UID, string photo_addr)
        {
            string ret = "T";

            string strCmd = string.Format("update UserInfo set Photo_Addr = '{0}' where UID = '{1}'",photo_addr,UID);//此处 {1} 一定要加上'' 不加的话 默认为int类型，而当uid为纯数字的话，可以通过，但是当存在字母时会卡死在这。。
            cmd.CommandText = strCmd;
            cmd.ExecuteNonQuery();

            return ret;
        }


        public void Close()
        {
            conn.Close();
        }

    }
}