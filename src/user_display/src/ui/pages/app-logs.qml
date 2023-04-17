import QtQuick
import QtQuick.Controls

Page {
    Label {
        text: "<html><h1>Displays the logs of the app itself</h1></html>"
        anchors{
            top:parent.top
            right:parent.right
            left: parent.left
        }
        horizontalAlignment: Label.AlignHCenter
        verticalAlignment: Label.AlignVCenter
        wrapMode: Label.Wrap
    }
}
