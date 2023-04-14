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
            Component.onCompleted: loadOtherQml("qrc:/designs/tabs/controls/src/ui/others/control-tabs/Semi-Automatic.qml",this)
        }
        Item{
            Component.onCompleted: loadOtherQml("qrc:/designs/tabs/controls/src/ui/others/control-tabs/Automatic.qml",this)
        }
        Item{
            Component.onCompleted: loadOtherQml("qrc:/designs/tabs/controls/src/ui/others/control-tabs/Manual.qml",this)
        }
    }

    function loadOtherQml(qmlFile, componentId) {
        var myTab = Qt.createComponent(qmlFile);
        if (myTab.status === Component.Ready) {
            myTab.createObject(componentId).anchors.fill=componentId
        }
    }

}


