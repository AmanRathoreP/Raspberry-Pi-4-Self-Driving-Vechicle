import QtQuick
import QtQuick.Controls

Page {

    property ListModel myModel: ListModel {
        ListElement { name: "Initial Item" }
    }

    function appendToList(name) {
        myModel.append({ "name": name })
    }

    ListView {
        width: parent.width
        height: parent.height

        model: myModel

        delegate: Label {
            width: parent.width
            text: name
            anchors.left: parent.left
            wrapMode: Text.Wrap
            Rectangle {
                height: 1
                width: parent.width
                color: "gray"
                anchors.bottom: parent.bottom
            }
        }

        Button {
            text: "Add item"
            anchors.bottom: parent.bottom
            anchors.horizontalCenter: parent.horizontalCenter
            onClicked: appendToList("New Item")
        }
    }
}
