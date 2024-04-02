package ru.toxyxd.item.component

interface TrackComponent {
    val id: String
    val title: String
    val artists: List<String>

    val artistsIds: List<String>
    val albumId: String?

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