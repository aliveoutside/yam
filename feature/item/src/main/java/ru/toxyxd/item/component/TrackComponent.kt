package ru.toxyxd.item.component

interface TrackComponent {
    val title: String
    val artist: String
    val cover: String
}

interface AlbumTrackComponent : TrackComponent {
    val index: Int
}

interface PlaylistTrackComponent : TrackComponent