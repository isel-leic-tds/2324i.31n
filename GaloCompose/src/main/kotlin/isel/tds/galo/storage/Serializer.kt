package isel.tds.galo.storage

interface Serializer<Data> {
    fun serialize(data: Data): String
    fun deserialize(data: String): Data
}