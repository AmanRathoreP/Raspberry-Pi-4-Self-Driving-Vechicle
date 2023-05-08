#include <QGuiApplication>
#include <QQmlApplicationEngine>
#include <QQmlContext>
#include "src/src/qt/myappsettingsclass.h"
#include "src/src/qt/vehiclecommunication.h"

int main(int argc, char *argv[])
{
    QGuiApplication::setApplicationName("User's Display");

    QGuiApplication app(argc, argv);

    myAppSettingsClass myAppSettings("app-settings.conf");
    myAppSettings.settingsList.append(qMakePair("showToolTips", true));
    myAppSettings.settingsList.append(qMakePair("delayForToolTipsToAppear", 50));

    for (int i = 0; i < myAppSettings.settingsList.size(); i++) {
        if (!myAppSettings.contains(myAppSettings.settingsList.at(i).first)) {
            myAppSettings.setValue(myAppSettings.settingsList.at(i).first, myAppSettings.settingsList.at(i).second);
        }
    }

    vehicleCommunication communication;

    QQmlApplicationEngine engine;
    engine.rootContext()->setContextProperty("iconHeight", 42);
    engine.rootContext()->setContextProperty("iconWidth", 42);
    engine.rootContext()->setContextProperty("myAppSettings", &myAppSettings);
    engine.rootContext()->setContextProperty("communication", &communication);

    engine.rootContext()->setContextProperty("showToolTips", false);
    engine.load(QUrl(u"qrc:/user_display/src/ui/Main.qml"_qs));
    if (engine.rootObjects().isEmpty())
        return -1;

    return app.exec();
}
