using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace PowerSNS_Svr.UserInformation
{
    public class UserInfo
    {
        public string Id { get; set; }
        public string Name { get; set; }
        public string Password { get; set; }
        public string Mood { get; set; }
        public int Age { get; set; }
        public string Gender { get; set; }
        public string Photo_Addr { get; set; }

        public UserInfo() { }

        public UserInfo(string _id,string _name,string _password, string _mood, int _age,string _gender, string _photo ) 
        {
            Id = _id;
            Name = _name;
            Password = _password;
            Mood = _mood;
            Age = _age;
            Photo_Addr = _photo;
        }

    }
}