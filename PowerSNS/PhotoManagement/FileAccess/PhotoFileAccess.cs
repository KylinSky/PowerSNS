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
                ///���岢ʵ����һ���ڴ������Դ���ύ�������ֽ����顣
                MemoryStream m = new MemoryStream(fs);
                ///����ʵ���ļ����󣬱������ص��ļ���
                FileStream f = new FileStream( path + FileName, FileMode.Create);
                ///�����ڴ��������д�������ļ�
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
            if (Directory.Exists(path)) //�����������ļ���ɾ��֮ 
            {
                foreach (string d in Directory.GetFileSystemEntries(path))
                {
                    if (File.Exists(d))
                        File.Delete(d); //ֱ��ɾ�����е��ļ� 
                    else
                        DeleteFolder(d); //�ݹ�ɾ�����ļ��� 
                }
                Directory.Delete(path); //ɾ���ѿ��ļ��� 
               // Response.Write(dir + " �ļ���ɾ���ɹ�");
            }
           // else
               // Response.Write(dir + " ���ļ��в�����"); //����ļ��в���������ʾ 
        }

        public void DeletePhoto(string delfile) {

            File.Delete(DefaultValue.DEFAULT_ALBUM_ROOT_FILEPATH + delfile);
        }



    }
}