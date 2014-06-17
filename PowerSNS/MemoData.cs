
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Data;
using System.Data.SqlClient;
using PowerSNS_Svr.UserInformation;

namespace PowerSNS
{
    class MemoData
    {
        private SqlConnection conn = null;
        private SqlCommand cmd;

        public MemoData()
        {
            conn = new SqlConnection();

            //conn.ConnectionString = @"Data Source=.\SQLEXPRESS;AttachDbFilename=D:\ASPNET\server\MyWebservice\App_Data\Memo.MDF ;Integrated Security=True;User Instance=True";
            conn.ConnectionString = MemoDatabaseAccess.connString;
            conn.Open();

            cmd = new SqlCommand();
            cmd.Connection = conn;
            cmd.CommandType = CommandType.Text;

        }


        //登录检验
        public bool LoginCheck(string name, string pass)
        {

            string strCmd = "select * from UserInfo where UID = \'" + name + "\' and Password = \'" + pass + "\'";
            cmd.CommandText = strCmd;

            SqlDataAdapter da = new SqlDataAdapter(cmd);

            cmd.ExecuteNonQuery();

            DataSet ds = new DataSet();
            da.Fill(ds);
            if (ds.Tables[0].Rows.Count == 1)
            {
                conn.Close();
                return true;
            }
            else
            {
                conn.Close();
                return false;
            }
;
        }

        //创建新用户
        public bool NewAccount(string UID, string pwd, string nickname)
        {
            string strCmd = "select * from UserInfo where UID = \'" + UID + "\'";
            cmd.CommandText = strCmd;

            SqlDataAdapter da = new SqlDataAdapter(cmd);

            cmd.ExecuteNonQuery();

            DataSet ds = new DataSet();
            da.Fill(ds);

            if(ds.Tables[0].Rows.Count == 1)
                return false;
           
            
            strCmd = "insert into UserInfo (UID,Password,UserName) values (\'" + UID + "\',\'" + pwd + "\',\'" + nickname + "\')";
            cmd.CommandText = strCmd;
            if (cmd.ExecuteNonQuery() == 1)
            {
                conn.Close();
                return true;
            }
            else
            {
                conn.Close();
                return false;
            }
       

        }

        //进入主页面后加载用户信息
        public string[] LoadMainPage(string UID)
        {

            string strCmd = "select Mood,UserName,Photo_Addr from UserInfo where UID =\'" + UID + "\'";
            cmd.CommandText = strCmd;

            SqlDataAdapter da = new SqlDataAdapter(cmd);
            cmd.ExecuteNonQuery();

            DataSet ds = new DataSet();
            da.Fill(ds);

            DataRow dr = ds.Tables[0].Rows[0];

            string[] str = new string[3];
            str[0] = dr[1].ToString();
            str[1] = dr[0].ToString();
            str[2] = dr[2].ToString();

            if (str[1].Trim().Equals(""))
                str[1] = "更新你的心情";
            conn.Close();
            return str;

        }

        //更新心情
        public void UpdateMood(string mood,string UID)
        {
            //string strCmd = "Update simpleUser set Mood = '" + mood + "' where UID = '" + UID + "'";
            string strCmd = string.Format("Update UserInfo set Mood='{0}' where UID = '{1}'",mood,UID);
            cmd.CommandText = strCmd;

            int i = cmd.ExecuteNonQuery();
            cmd.ExecuteNonQuery();
            conn.Close();
        }


        //写日志
        public bool WriteDiary(string UID, string Title, string content)
        {
            String dayNow = System.DateTime.Now.ToString("yyyy-MM-dd");
            string strCmd = "insert into Diary (UID,Title,DContent,DDate) values(\'"
                + UID + "\',\'" + Title + "\',\'" + content + "\',\'" + dayNow + "\')";
            cmd.CommandText = strCmd;

            if (cmd.ExecuteNonQuery() >= 1)
            {
                conn.Close();
                return true;
            }
            else
            {
                conn.Close();
                return false;
            }

            
        }


        //获取日志列表  : did  +  title
        public string[] GetDiaryList(string UID)
        {

            string strCmd = "select DID,Title,DDate,DContent from Diary where UID = \'" + UID + "\'";
            cmd.CommandText = strCmd;
            SqlDataAdapter da = new SqlDataAdapter(cmd);
            cmd.ExecuteNonQuery();

            DataSet ds = new DataSet();
            da.Fill(ds);


            string[] str = new string[4];
            str[0] = "";
            str[1] = "";
            str[2] = "";
            str[3] = "";

            int len = ds.Tables[0].Rows.Count;
            for (int i = 0; i < len; i++)
            {
                DataRow dr = ds.Tables[0].Rows[i];
                str[0] += dr[0].ToString() + "\n";
                str[1] += dr[1].ToString() + "\n";
                string datenow = Convert.ToDateTime(dr[2].ToString()).ToString("d");
                str[2] += datenow + "\n";
                str[3] += dr[3].ToString() + "\n";
            }

            conn.Close();
            return str;
        }



        //发出好友申请
        public string  FriendRequest(string UID1, string UID2)
        {
            string ret = "F";


            //检查该用户是否存在，不存在返回N
            //检查该用户是否为自己好友，是则返回E
            //检查好友申请是否已发出，发出则返回R
            //不符合上述条件则再请求加入数据库
            //

            string strCmd = "select * from userInfo where UID= \'" + UID2 + "\'";
            cmd.CommandText = strCmd;
            SqlDataAdapter da = new SqlDataAdapter(cmd); 
            cmd.ExecuteNonQuery();

            DataSet ds = new DataSet();
            da.Fill(ds);

            if (ds.Tables[0].Rows.Count == 0 || UID2.Equals(UID1))
            {
                ret = "N";
                conn.Close();
                return ret;
            }



            //首先检查有无此好友
            strCmd = "select * from FriendList where UID1 = \'" + UID1 + "\' and UID2 = \'" + UID2 + "\'";
            cmd.CommandText = strCmd;
            SqlDataAdapter da1 = new SqlDataAdapter(cmd);
            cmd.ExecuteNonQuery();

            ds = new DataSet();
            da1.Fill(ds);

            if (ds.Tables[0].Rows.Count == 1)
            {
                ret = "E";
                conn.Close();
                return ret;
            }


            //其实检查是否已提交申请
            strCmd = string.Format("select * from FriendRequestList where UID1 = \'{0}\' and UID2 = \'{1}\'",UID1,UID2);
            cmd.CommandText = strCmd;
            SqlDataAdapter da2 = new SqlDataAdapter(cmd);
            cmd.ExecuteNonQuery();

            ds = new DataSet();
            da2.Fill(ds);

            if (ds.Tables[0].Rows.Count == 1)
            {
                ret = "R";
                conn.Close();
                return ret;
            }

            //检查是否已接到好友请求
            strCmd = string.Format("select * from FriendRequestList where UID1 = \'{0}\' and UID2 = \'{1}\'", UID2, UID1);
            cmd.CommandText = strCmd;
            SqlDataAdapter da3 = new SqlDataAdapter(cmd);
            cmd.ExecuteNonQuery();

            ds = new DataSet();
            da3.Fill(ds);

            if (ds.Tables[0].Rows.Count == 1)
            {
                ret = "A";
                conn.Close();
                return ret;
            }

      
    
            //将请求添加到数据库
             strCmd = string.Format("insert into FriendRequestList (UID1,UID2) values (\'{0}\',\'{1}\')",UID1,UID2);
             cmd.CommandText = strCmd;
             cmd.ExecuteNonQuery();



             ret = "Y";
            
             return ret;
        }



        //获取好友申请列表
        public string[] getFriendRequestList(string UID)
        {

            string strCmd = string.Format("select F.UID1,U.UserName from UserInfo U,FriendRequestList F where F.UID2 = \'{0}\' and F.UID1 = U.UID", UID);
            cmd.CommandText = strCmd;
            SqlDataAdapter da = new SqlDataAdapter(cmd);
            cmd.ExecuteNonQuery();

            DataSet ds = new DataSet();
            da.Fill(ds);

            string[] str = new string[2];
            str[0] = "";
            str[1] = "";

            int len = ds.Tables[0].Rows.Count;
            for(int i = 0 ; i < len; i++)
            {
                DataRow dr = ds.Tables[0].Rows[i];
                str[0] += dr[0] + "\n";
                str[1] += dr[1] + "\n";
            }


            conn.Close();
            return str;
        }



        //确认添加好友 或 拒绝申请
        public string addFriend(string UID1, string UID2,string flag)
        {
            string ret = "Y";
            string strCmd;
            if (flag.Equals("A"))
            {
                strCmd = string.Format("insert into FriendList (UID1,UID2) values (\'{0}\',\'{1}\')", UID1, UID2);
                cmd.CommandText = strCmd;
                cmd.ExecuteNonQuery();

                strCmd = string.Format("insert into FriendList (UID1,UID2) values (\'{0}\',\'{1}\')", UID2, UID1);
                cmd.CommandText = strCmd;
                cmd.ExecuteNonQuery();
            }

            strCmd = string.Format ("delete from  FriendRequestList where UID1 = \'{0}\' and UID2 = \'{1}\'",UID2,UID1);
            cmd.CommandText = strCmd;
            cmd.ExecuteNonQuery();

            conn.Close();
            return ret;
        }



        //获取好友列表
        public string[] getFriendList(string UID)
        {
            string[] str = new string[2];

            string strCmd = string.Format("select F.UID2,U.UserName from UserInfo U,FriendList F where F.UID1 = \'{0}\' and F.UID2 = U.UID", UID);
            //string strCmd = string.Format("select UID1,UID2 from FriendList where UID1 = \'{0}\'",UID);
            cmd.CommandText = strCmd;
            SqlDataAdapter da = new SqlDataAdapter(cmd);

            cmd.ExecuteNonQuery();

            DataSet ds = new DataSet();

            da.Fill(ds);
            str[0] = "";
            str[1] = "";
            
            int len = ds.Tables[0].Rows.Count;

            for (int i = 0; i < len; i++)
            {
                DataRow dr = ds.Tables[0].Rows[i];
                str[0] += dr[0] + "\n";
                str[1] += dr[1] + "\n";
            }

            conn.Close();
            return str;
        }


        public List<UserInfo> GetFriendInfo(string UID)
        {
            List<UserInfo> lui = new List<UserInfo>();

            string strCmd = string.Format("select F.UID2,U.UserName,U.Mood,U.Photo_Addr from UserInfo U,FriendList F where F.UID1 = \'{0}\' and F.UID2 = U.UID", UID);
            cmd.CommandText = strCmd;
            SqlDataAdapter da = new SqlDataAdapter(cmd);

            cmd.ExecuteNonQuery();

            DataSet ds = new DataSet();
            da.Fill(ds);

            int len = ds.Tables[0].Rows.Count;
            for (int i = 0; i < len; i++)
            {
                UserInfo ui = new UserInfo();
                DataRow dr = ds.Tables[0].Rows[i];
                ui.Id = dr[0].ToString();
                ui.Name = dr[1].ToString();
                ui.Mood = dr[2].ToString();
                ui.Photo_Addr = dr[3].ToString();
                lui.Add(ui);
            }


             return lui;
        }


        public string DeleteFriend(string UID ,string fUid)
        {
            string strCmd = string.Format("Delete from FriendList where UID1 = '{0}' and UID2 = '{1}'",UID,fUid);
            cmd.CommandText = strCmd;
            cmd.ExecuteNonQuery();

            strCmd = string.Format("Delete from FriendList where UID1 = '{0}' and UID2 = '{1}'", fUid, UID);
            cmd.CommandText = strCmd;
            cmd.ExecuteNonQuery();


            return "OK";
        }


        //读取日志内容
        public string[] ReadDiary(string did)
        {
            string strCmd = "select Title,DContent from Diary where did = \'" + did + "\'";
            cmd.CommandText = strCmd;
            SqlDataAdapter da = new SqlDataAdapter(cmd);
            cmd.ExecuteNonQuery();

            DataSet ds = new DataSet();
            da.Fill(ds);

            string[] str =  new string[2];

            DataRow dr = ds.Tables[0].Rows[0];
            str[0] = dr[0].ToString() ;
            str[1] = dr[1].ToString();
            conn.Close();
            return str;
        }


        public string DeleteDiary(string did)
        {
            string strCmd = string.Format("Delete from Diary where DID = '{0}'", did);
            cmd.CommandText = strCmd;
            cmd.ExecuteNonQuery();


            conn.Close();
            return "OK";
        }


        public string UpdateDiary(string DContent, int did)
        {
            string strCmd = string.Format("Update Diary set DContent = '{0}' where DID = '{1}'", DContent, did);

            cmd.CommandText = strCmd;
            cmd.ExecuteNonQuery();

            conn.Close();
            return "OK";
        }

    }


    
}
