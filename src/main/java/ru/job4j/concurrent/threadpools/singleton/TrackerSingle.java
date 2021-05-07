package ru.job4j.concurrent.threadpools.singleton;

/* Энергичная загрузка. Объект создается сразу:  */

/**
 * 1.1. Реализация с применением enum, аналогична однопоточной реализации.
 * Объект enum создается при загрузке класса и безопасно публикуется всем клиентам
 */
public enum TrackerSingle {
    INSTANCE;

   /* public Item add(Item model) {
        return model;
    }*/

    public static void main(String[] args) {
        TrackerSingle tracker = TrackerSingle.INSTANCE;
    }
}


/**
 * 1.2. Реализация с применением поля final.
 */
class TrackerSingle2 {

    private static final TrackerSingle2 INSTANCE = new TrackerSingle2();

    private TrackerSingle2() {
    }

    public static TrackerSingle2 getInstance() {
        return INSTANCE;
    }

     /* public Item add(Item model) {
        return model;
    }*/

    public static void main(String[] args) {
        TrackerSingle2 tracker = TrackerSingle2.getInstance();
    }
}

/* 2. Ленивая загрузка: */

/**
 * 2.1. Single checked locking.
 * Инициализация и проверка экземпляра происходит внутри критической секции. Этот шаблон деградирует производительность.
 * Инициализация и проверка экземпляра происходит внутри критической секции. Этот шаблон деградирует производительность.
 * Использовать этот шаблон не рекомендуется.
 */
class TrackerSingle3 {

    private static TrackerSingle3 INSTANCE;

    private TrackerSingle3() {
    }

    public static synchronized TrackerSingle3 getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TrackerSingle3();
        }
        return INSTANCE;
    }

     /* public Item add(Item model) {
        return model;
    }*/

    public static void main(String[] args) {
        TrackerSingle3 tracker = TrackerSingle3.getInstance();
    }
}

/**
 * 2.2. Double checked locking.
 * Поле экземпляра обозначено volatile. Это обеспечит решение проблемы видимости после инициализации поля.
 * Первая проверка экземпляра идет до блока синхронизации, что позволяет улучшить скорость работы по сравнению с single checked locking реализацией.
 * В критической секции происходит инициализация экземпляра и запись его в переменную.
 * Этот шаблон использовать не рекомендуется. Он уменьшает производительность системы при многопроцессорном окружении.
 */
class TrackerSingle4 {

    private static volatile TrackerSingle4 INSTANCE;

    private TrackerSingle4() {
    }

    public static TrackerSingle4 getInstance() {
        if (INSTANCE == null) {
            synchronized (TrackerSingle4.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TrackerSingle4();
                }
            }
        }
        return INSTANCE;
    }

     /* public Item add(Item model) {
        return model;
    }*/

    public static void main(String[] args) {
        TrackerSingle4 tracker = TrackerSingle4.getInstance();
    }
}

/**
 *  2.3. Holder.
 *  Реализация этого шаблона аналогична однопоточной среде.
 *  Его работа стабильна и не влияет на производительность системы.
 */
    class TrackerSingle5 {
    private TrackerSingle5() {
    }

    public static TrackerSingle5 getInstance() {
        return Holder.INSTANCE;
    }

    private static final class Holder {
        private static final TrackerSingle5 INSTANCE = new TrackerSingle5();
    }

     /* public Item add(Item model) {
        return model;
    }*/

    public static void main(String[] args) {
        TrackerSingle5 tracker = TrackerSingle5.getInstance();
    }

    /* JVM откладывает инициализацию класса Holder до тех пор, пока он не будет фактически использован,
    и поскольку синглтон инициализируется статическим инициализатором, дополнительная синхронизация не требуется.
    Первый вызов getInstance любым потоком вызывает загрузку и инициализацию Holder,
    в это время инициализация синглтона происходит через статический инициализатор.
    */
}

/* Вывод:

Если у вас нет необходимости в ленивой загрузке, то используйте шаблоны из первой группы. Например, инициализация кэша или базы данных.

Если в приложении есть затратные ресурсы нужно использовать шаблоны с ленивой загрузкой. Здесь можно использовать только один шаблон - это Holder.

Другие шаблоны будут отрицательно влиять на производительность системы.

*/
