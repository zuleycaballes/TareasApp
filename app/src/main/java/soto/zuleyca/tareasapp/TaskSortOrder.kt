package soto.zuleyca.tareasapp

enum class TaskSortOrder(val label: String) {
    NEWEST("Más recientes primero"),
    OLDEST("Más antiguas primero"),
    TITLE_A_Z("Título A-Z"),
    TITLE_Z_A("Título Z-A")
}