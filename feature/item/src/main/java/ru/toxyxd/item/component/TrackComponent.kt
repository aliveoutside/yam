package ru.toxyxd.item.component

interface TrackComponent {
    val id: String
    val title: String
    val artist: String
    val cover: String
    val hugeCover: String
}

interface AlbumTrackComponent : TrackComponent {
    val index: Int
}

interface PlaylistTrackComponent : TrackComponent