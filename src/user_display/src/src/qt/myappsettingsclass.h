
#ifndef MYAPPSETTINGSCLASS_H
#define MYAPPSETTINGSCLASS_H

#include <QSettings>



class myAppSettingsClass : public QSettings
{
    Q_OBJECT
public:
    myAppSettingsClass(const QString &fileNameToSaveSettings);

public:
    Q_INVOKABLE QVariant get_value(QString value_name);
    Q_INVOKABLE void set_value(QString value_name, bool value_to_put);
    Q_INVOKABLE void set_value(QString value_name, int value_to_put);
    Q_INVOKABLE void reset_settings(void);

public:
    QList<QPair<QString, QVariant>> settingsList;

};

#endif // MYAPPSETTINGSCLASS_H
