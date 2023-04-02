import QtQuick

Window {
    visible: true
    title: qsTr("Home Scene")

    Text {
        color: "#b3030303"
        text: qsTr("This is a \nHome Screen!")
        font.pixelSize: 34
        horizontalAlignment: Text.AlignHCenter
        anchors.verticalCenterOffset: 1
        anchors.horizontalCenterOffset: 0
        scale: 1
        font.weight: Font.ExtraLight
        font.family: "Times New Roman"
        anchors.centerIn: parent
    }
}
