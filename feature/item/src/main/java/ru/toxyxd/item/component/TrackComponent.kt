package ru.toxyxd.item.component

interface TrackComponent {
    val id: String
    val title: String
    val artist: String
    val cover: String?
    val hugeCover: String?
    val duration: Long
}

interface AlbumTrackComponent : TrackComponent {
    val index: Int
}

interface TopTrackComponent : TrackComponent {
    val index: Int
}

interface PlaylistTrackComponent : TrackComponent