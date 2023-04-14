import QtQuick
import QtQuick.Controls

Page {

    header: TabBar {
        id: tabBar
        currentIndex: swipeView.currentIndex

        TabButton {
            text: "Semi-Automatic"
            ToolTip {
                delay:50
                text: "Provides semeless transition between the automatic and the manual controls"
                visible: parent.hovered
            }
        }
        TabButton {
            text: "Automatic"
            ToolTip {
                delay:50
                text: "Full automatic system no need for any human driver"
                visible: parent.hovered
            }
        }
        TabButton {
            text: "Manual"
            ToolTip {
                delay:50
                text: "Just shows some extra information about the environment but only driver can control hardware of the vehicle"
                visible: parent.hovered
            }
        }
    }


    SwipeView {
        id: swipeView
        anchors.fill: parent
        currentIndex: tabBar.currentIndex

        Item{
            Label {
                text: "<html><body><h1>Semi-Automatic Control stuff</h1></body></html>"
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
        Item{
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
        Item{
            Label {
                text: "<html><body><h1>Manual Control stuff</h1></body></html>"
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
        }


}


