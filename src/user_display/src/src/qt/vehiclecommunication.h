
#ifndef VEHICLECOMMUNICATION_H
#define VEHICLECOMMUNICATION_H

#include <QObject>
#include <QDebug>
#include <QTcpServer>
#include <QTcpSocket>
#include <QtConcurrent>

class vehicleCommunication : public QObject
{
    Q_OBJECT
public:
    explicit vehicleCommunication(QObject *parent = nullptr);
    void initCommunication(void);
    Q_INVOKABLE void startSendData(void);
    Q_INVOKABLE void startRecvData(void);
    Q_INVOKABLE void updateData(QString vehicle_control_type, QString vehicle_direction_to_move_in, int vehicle_right_wheel_speed, int vehicle_left_wheel_speed, int vehicle_max_speed);
    Q_INVOKABLE int getSpeed(void);
    Q_INVOKABLE bool getIsConnected(void);
    Q_INVOKABLE QString getRaw_data_received();

private:
    QTcpServer server;
    QTcpSocket* clientSocket;
    QString raw_data_received = "";
    QString vehicle_control_type = "m";
    QString vehicle_direction_to_move_in = "f";
    int vehicle_right_wheel_speed = 65000;
    int vehicle_left_wheel_speed = 65000;
    int vehicle_max_speed = 65001;
    bool isConnected;
};

#endif // VEHICLECOMMUNICATION_H
