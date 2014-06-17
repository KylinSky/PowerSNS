using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.IO;
using PowerSNS_Svr.PhotoManagement.Enum;

namespace PowerSNS_Svr.PhotoManagement.FileAccess
{
    public class PhotoFileAccess
    {
        public PhotoFileAccess() { }

        public string  CreateAlbum(string albumname) {
            string path =DefaultValue.DEFAULT_ALBUM_ROOT_FILEPATH  +albumname+"\\";
            if (Directory.Exists(path))
            {
                return null;
            }
            else
            {
                Directory.CreateDirectory(path);
                return albumname;
            }
        }

        public string CreateFile(string folder , string FileName , byte[] fs)  
        {
            
            string path = DefaultValue.DEFAULT_ALBUM_ROOT_FILEPATH + folder;
            if (!Directory.Exists(path))
            {
                Directory.CreateDirectory(path);
            }
           try{
                ///定义并实例化一个内存流，以存放提交上来的字节数组。
                MemoryStream m = new MemoryStream(fs);
                ///定义实际文件对象，保存上载的文件。
                FileStream f = new FileStream( path + FileName, FileMode.Create);
                ///把内内存里的数据写入物理文件
                m.WriteTo(f);
                m.Close();
                f.Close();
                f = null;
                m = null;
                return folder + FileName;
            }
            catch (Exception ex)
            {
                return null;
            }
           
        }


        public void DeleteFolder(string f_album)
        {
            string path = DefaultValue.DEFAULT_ALBUM_ROOT_FILEPATH + f_album;
            if (Directory.Exists(path)) //如果存在这个文件夹删除之 
            {
                foreach (string d in Directory.GetFileSystemEntries(path))
                {
                    if (File.Exists(d))
                        File.Delete(d); //直接删除其中的文件 
                    else
                        DeleteFolder(d); //递归删除子文件夹 
                }
                Directory.Delete(path); //删除已空文件夹 
               // Response.Write(dir + " 文件夹删除成功");
            }
           // else
               // Response.Write(dir + " 该文件夹不存在"); //如果文件夹不存在则提示 
        }

        public void DeletePhoto(string delfile) {

            File.Delete(DefaultValue.DEFAULT_ALBUM_ROOT_FILEPATH + delfile);
        }



    }
}