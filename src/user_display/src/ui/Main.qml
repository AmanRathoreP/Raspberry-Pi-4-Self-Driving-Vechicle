import QtCore
import QtQuick
import QtQuick.Layouts
import QtQuick.Controls
import QtQuick.Controls.Material


ApplicationWindow {
    id: window
    width: 360
    height: 520
    visible: true
    title: "User Display for SDV"

    Shortcut {
        sequences: ["Esc", "Back"]
        enabled: stackView.depth > 1
        onActivated: navigateBackAction.trigger()
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
                icon.source: stackView.depth > 1 ? "qrc:/graphics/images/icons/resources/icons/back.svg" : "qrc:/graphics/images/icons/resources/icons/menu.svg"
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
                icon.source: "qrc:/graphics/images/icons/resources/icons/dot-stack.svg"
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
                contentItem: Row {
                    spacing: 10
                    Image {
                        source: model.iconSrc
                        width: parent.height
                        height: parent.height
                    }
                    Text {
                        text: model.title
                        font.pixelSize: 20
                    }
                }
                highlighted: ListView.isCurrentItem
                onClicked: {
                    listView.currentIndex = index
                    stackView.push(Qt.createComponent(model.source))
                    drawer.close()
                }
            }


            model: ListModel {
                ListElement { title: "Vehicle's Logs"; source: "./pages/vehicle-logs.qml"; iconSrc: "qrc:/graphics/images/icons/resources/icons/vehicle-logs.svg" }
                ListElement { title: "App's Logs"; source: "./pages/app-logs.qml"; iconSrc: "qrc:/graphics/images/icons/resources/icons/logs.svg" }
                ListElement { title: "App Settings"; source: "./pages/app-settings.qml"; iconSrc: "qrc:/graphics/images/icons/resources/icons/settings.svg" }
            }

            ScrollIndicator.vertical: ScrollIndicator { }
        }
    }

    StackView {
        id: stackView
        anchors.fill: parent
        initialItem: Qt.createComponent("./pages/home.qml")
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
