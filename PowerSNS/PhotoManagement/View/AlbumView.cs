using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace PowerSNS_Svr.PhotoManagement.View
{
    [Serializable]
    public class AlbumView
    {
        public int _id{get;set;}
        public string user_id{get;set;}
        public string name{get;set;}
        public string descript { get; set; }
        public int size { get; set; }
        public string face_addr { get; set; }
        public string network_addr { get; set; }
        public string file_path { get; set; }

        public AlbumView() { }
        public AlbumView(int Id, string User_id,string Name, string Descript,int Size,string facepic)
        {
            _id = Id;
            name = Name;
            descript = Descript; 
            user_id = User_id; 
            size = Size;
            face_addr = facepic;
        }
    }
}