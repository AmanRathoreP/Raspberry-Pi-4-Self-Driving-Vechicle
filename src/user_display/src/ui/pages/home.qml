import QtQuick
import QtQuick.Controls

Page {
    Label {
        text: "<html><body>Home screen is the place where user can select weather he/she wants<br><ul><li>Semi-Automatic Control</li><li>Automatic Control</li><li>Manual Control</li></ul></body></html>"
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
