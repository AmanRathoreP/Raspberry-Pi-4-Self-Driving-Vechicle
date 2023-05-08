
#include "vehiclecommunication.h"

vehicleCommunication::vehicleCommunication(QObject *parent)
    : QObject{parent}
{
    this->initCommunication();
}

void vehicleCommunication::initCommunication(void)
{
    QFuture<void> future = QtConcurrent::run([this]() {
        this->server.listen(QHostAddress::Any, 8011);
        qDebug() << "Server started at" << this->server.serverAddress().toString() << "on port" << this->server.serverPort();
        qDebug() << "Looking for requests";
        this->server.waitForNewConnection(-1);
        this->clientSocket = this->server.nextPendingConnection();
        qDebug() << "New connection made! at address =" << this->clientSocket->peerAddress().toString();
        this->isConnected = true;

        while(this->isConnected)
        {
            startRecvData();
            startSendData();
        }
    });
}

void vehicleCommunication::startSendData(void)
{
    if (this->clientSocket && this->clientSocket->state() == QAbstractSocket::ConnectedState) {
        QString data = "{" +
                       this->vehicle_control_type + "@" +
                       this->vehicle_direction_to_move_in + "@" +
                       QString::number(this->vehicle_right_wheel_speed) + "@" +
                       QString::number(this->vehicle_left_wheel_speed) + "@" +
                       QString::number(this->vehicle_max_speed) +
                       "}";
        this->clientSocket->write(data.toUtf8().data());
        this->clientSocket->waitForBytesWritten();
        QThread::sleep(0.09);
    }
}

void vehicleCommunication::startRecvData()
{
    //    while(this->clientSocket->waitForReadyRead(-1) && this->isConnected) {
    if (this->clientSocket->bytesAvailable() > 0) {
    this->raw_data_received = QString(this->clientSocket->readAll());
    }
    qDebug() << this->raw_data_received;
    //    }
}

void vehicleCommunication::updateData(QString vehicle_control_type, QString vehicle_direction_to_move_in, int vehicle_right_wheel_speed, int vehicle_left_wheel_speed, int vehicle_max_speed)
{
    this->vehicle_control_type = vehicle_control_type;
    this->vehicle_direction_to_move_in = vehicle_direction_to_move_in;
    this->vehicle_right_wheel_speed = vehicle_right_wheel_speed;
    this->vehicle_left_wheel_speed = vehicle_left_wheel_speed;
    this->vehicle_max_speed = vehicle_max_speed;
}

int vehicleCommunication::getSpeed()
{
    return 1212;
}

bool vehicleCommunication::getIsConnected(void)
{
    return isConnected;
}

QString vehicleCommunication::getRaw_data_received()
{
    return this->raw_data_received;
}
