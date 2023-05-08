import QtQuick
import QtQuick.Controls

Page {

    header: TabBar {
        id: tabBar
        currentIndex: swipeView.currentIndex

        TabButton {
            text: "Hybrid"
            ToolTip {
                delay: parseInt(myAppSettings.get_value("delayForToolTipsToAppear"))
                text: "Provides semeless transition between the automatic and the manual controls"
                visible: (parent.hovered || parent.pressed) && String(myAppSettings.get_value("showToolTips")).indexOf("t") !== -1 ? true : false
            }
        }
        TabButton {
            text: "Automatic"
            ToolTip {
                delay: parseInt(myAppSettings.get_value("delayForToolTipsToAppear"))
                text: "Full automatic system no need for any human driver"
                visible: (parent.hovered || parent.pressed) && String(myAppSettings.get_value("showToolTips")).indexOf("t") !== -1 ? true : false
            }
        }
        TabButton {
            text: "Manual"
            ToolTip {
                delay: parseInt(myAppSettings.get_value("delayForToolTipsToAppear"))
                text: "Just shows some extra information about the environment but only driver can control hardware of the vehicle"
                visible: (parent.hovered || parent.pressed) && String(myAppSettings.get_value("showToolTips")).indexOf("t") !== -1 ? true : false
            }
        }
    }


    SwipeView {
        id: swipeView
        anchors.fill: parent
        currentIndex: tabBar.currentIndex
        interactive: String(myAppSettings.get_value("allowSwipeModeSwitch")).indexOf("t") !== -1 ? true : false

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


