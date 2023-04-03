#include <QGuiApplication>
#include <QQmlApplicationEngine>

int main(int argc, char *argv[])
{
    QGuiApplication::setApplicationName("User's Display");

    QGuiApplication app(argc, argv);

    QQmlApplicationEngine engine;

    engine.load(QUrl(u"qrc:/user_display/src/ui/home.qml"_qs));
    if (engine.rootObjects().isEmpty())
        return -1;

    return app.exec();
}
