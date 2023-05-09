import QtQuick
import QtQuick.Controls

Item {
    Row{
        padding: 5
        anchors {
            left: parent.left
            right: parent.right
            bottom: parent.bottom
            top: parent.top
        }
        spacing: 5
        Label {
            text: "<html><body><h1>Automatic Control stuff</h1></body></html>"
            anchors{
                horizontalCenter: parent.horizontalCenter
            }
            wrapMode: Label.Wrap
        }

        Button {
            id: enableAutomaticControl
            text: "Start Auto-Pilot"
            highlighted: true
            anchors{
                horizontalCenter: parent.horizontalCenter
                verticalCenter: parent.verticalCenter
            }
            onClicked: communication.updateData("a", "s", 0, 0, 0)
        }
    }
}
