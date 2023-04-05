import QtCore
import QtQuick
import QtQuick.Layouts
import QtQuick.Controls


ApplicationWindow {
    id: window
    width: 360
    height: 520
    visible: true
    title: "User Display for SDV"


    Settings {
        id: settings
        property string style
    }


    Action {
        id: navigateBackAction
        onTriggered: {
            if (stackView.depth > 1) {
                stackView.pop()
                listView.currentIndex = -1
            } else {
                drawer.open()
            }
        }
    }


    Action {
        id: optionsMenuAction
        onTriggered: optionsMenu.open()
    }

    header: ToolBar {
        RowLayout {
            spacing: 20
            anchors.fill: parent

            ToolButton {
                icon.source: stackView.depth > 1 ? "https://www.svgrepo.com/show/238203/backward.svg" : "https://www.svgrepo.com/show/509382/menu.svg"
                action: navigateBackAction
            }

            Label {
                id: titleLabel
                text: listView.currentItem ? listView.currentItem.text : "Home Screen"
                font.pixelSize: 20
                elide: Label.ElideRight
                horizontalAlignment: Qt.AlignHCenter
                verticalAlignment: Qt.AlignVCenter
                Layout.fillWidth: true
            }

            ToolButton {
                icon.source: "https://www.svgrepo.com/show/452277/dot-stack.svg"
                action:optionsMenuAction

                Menu {
                    id: optionsMenu
                    x: parent.width - width
                    transformOrigin: Menu.TopRight

                    Action {
                        text: "Help"
                        onTriggered:Qt.openUrlExternally("https://github.com/AmanRathoreP/Raspberry-Pi-4-Self-Driving-Vechicle")
                    }
                    Action {
                        text: "About"
                        onTriggered: aboutDialog.open()
                    }
                }
            }
        }
    }

    Drawer {
        id: drawer
        width: Math.min(window.width, window.height) / 3 * 2
        height: window.height
        interactive: stackView.depth === 1

        ListView {
            id: listView

            focus: true
            currentIndex: -1
            anchors.fill: parent

            delegate: ItemDelegate {
                width: listView.width
                text: model.title
                highlighted: ListView.isCurrentItem
                onClicked: {
                    listView.currentIndex = index
                    stackView.push(Qt.createComponent(model.source))
                    drawer.close()
                }
            }

            model: ListModel {
                ListElement { title: "Vehicle's Logs"; source: "./pages/logger.qml" }
            }

            ScrollIndicator.vertical: ScrollIndicator { }
        }
    }

    StackView {
        id: stackView
        anchors.fill: parent

        initialItem: Pane {
            id: pane

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
    }



    Dialog {
        id: aboutDialog
        modal: true
        focus: true
        title: "About"
        x: (window.width - width) / 2
        y: window.height / 6
        width: Math.min(window.width, window.height) / 3 * 2
        contentHeight: aboutColumn.height

        Column {
            id: aboutColumn
            spacing: 20

            Label {
                width: aboutDialog.availableWidth
                text: "This apps provides freedom to user to interact with SDV in real time with best performance!"
                wrapMode: Label.Wrap
                font.pixelSize: 24
            }
        }
    }

}
