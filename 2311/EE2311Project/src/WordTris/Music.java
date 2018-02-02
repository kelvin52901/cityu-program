/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WordTris;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
/**
 *
 * @author user
 */
public class Music {
  Clip clip; 
  boolean isBGM;
  File file;
    public Music(String path,Boolean isBGM ){
        file=new File(path);
        this.isBGM=isBGM;
  }  
    public void play(){
        try{
            clip=AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(file));
            clip.start();
            Thread.sleep(clip.getMicrosecondLength());
        }catch(LineUnavailableException e){
    } catch (UnsupportedAudioFileException e) {
      } catch (IOException e) {
          System.out.printf(file.toString());
      } catch (InterruptedException e) {
      }
}
}