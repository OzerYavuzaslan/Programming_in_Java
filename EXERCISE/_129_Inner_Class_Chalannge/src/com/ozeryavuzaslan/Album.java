package com.ozeryavuzaslan;

import java.util.ArrayList;
import java.util.LinkedList;

public class Album {
    private String name;
    private String artist;
    private SongList songs;

    public Album(String name, String artist) {
        setName(name);
        setArtist(artist);
        setSongs(new SongList());
    }

    public boolean addSong(String title, double duration) {
        return getSongs().add(new Song(title, duration));
    }

    public boolean addToPlayList(int trackNumber, LinkedList<Song> playList) {
        Song checkedSong = getSongs().findSong(trackNumber);

        if (checkedSong != null){
            playList.add(checkedSong);
            return true;
        }

        System.out.println("This album does not have a track " + trackNumber);
        return false;
    }

    public boolean addToPlayList(String title, LinkedList<Song> playList) {
        Song checkedSong = getSongs().findSong(title);

        if(checkedSong != null) {
            playList.add(checkedSong);
            return true;
        }

        System.out.println("The song " + title + " is not in this album");
        return false;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setSongs(SongList songs) {
        this.songs = songs;
    }

    public SongList getSongs() {
        return songs;
    }

    private class SongList{
        private ArrayList<Song> songs;

        public SongList() {
            this.songs = new ArrayList<Song>();
        }

        public boolean add(Song song){
            if (getSongs().contains(song))
                return false;

            getSongs().add(song);
            return true;
        }

        private Song findSong(String title) {
            for(Song checkedSong: this.songs)
                if(checkedSong.getTitle().equals(title))
                    return checkedSong;

            return null;
        }

        public Song findSong(int trackNumber){
            int index = trackNumber - 1;

            if(index >= 0 && index < getSongs().size())
                return getSongs().get(index);

            return null;
        }

        public ArrayList<Song> getSongs() {
            return songs;
        }
    }
}