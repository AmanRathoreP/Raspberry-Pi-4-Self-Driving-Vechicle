import QtQuick
import QtQuick.Controls

Item {
    Label {
        text: "<html><body><h1>Automatic Control stuff</h1></body></html>"
        anchors{
            margins: 20
            left: parent.left
            right: parent.right
        }
        horizontalAlignment: Label.AlignHCenter
        verticalAlignment: Label.AlignVCenter
        wrapMode: Label.Wrap
    }
}
