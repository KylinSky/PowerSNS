using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Data;
using System.Data.SqlClient;

namespace PowerSNS
{
    public class MemoDatabaseAccess
    {

        //public static string connString = @"Data Source=.;AttachDbFilename=D:\ASPNET\server\MyWebservice\App_Data\Memo.MDF ;Integrated Security=True;User Instance=True";
       public static string connString = @" Data Source=.;Initial Catalog=Memo;User ID='sa';Password='sasa'";
        public SqlConnection conn = null;
        public SqlCommand cmd = null;

        public MemoDatabaseAccess()
        {
            conn = new SqlConnection();
            conn.ConnectionString = connString;
            
        }

        public void Open()
        {
            conn.Open();
            cmd.Connection = conn;
            cmd.CommandType = CommandType.Text;
        }

 

        public void Close()
        {
            conn.Close();
        }

    }
}