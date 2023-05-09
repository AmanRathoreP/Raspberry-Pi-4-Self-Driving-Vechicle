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

        RangeSlider {
            id: speedRangeSlider
            orientation: Qt.Vertical
            from: 0
            to: 65535
            first.value: 26214
            second.value: 62258
            height: parent.height
            width: 50
            first.onMoved: updateData();
            second.onMoved: updateData();
        }

        Slider {
            id: speedSlider
            orientation: Qt.Vertical
            from: 0
            to: 1
            value: 0
            height: parent.height
            width: 50
            onValueChanged: {
                if (pressed) {
                    //                    value = 0;
                    updateData();
                }

            }
        }

        Column{
            id: reverseColumn
            height: parent.height

            Rectangle{
                height: reverseColumn.height - (reverseSwitch.height*2.5)
                width: reverseColumn.width
                color: "transparent"
                anchors{
                    horizontalCenter: reverseColumn.horizontalCenter
                }
            }

            Label{
                text: "Reverse"
                anchors{
                    horizontalCenter: reverseColumn.horizontalCenter
                }
            }

            Switch {
                id: reverseSwitch
                checked: false // Set the initial value
                anchors{
                    horizontalCenter: reverseColumn.horizontalCenter
                }
                onClicked: {
                    updateData();
                }
            }

        }

        Rectangle{
            height: parent.height
            width: parent.width - (reverseColumn.width + speedRangeSlider.width + speedSlider.width + (steeringDial.width * 2))
            color: "transparent"

        }

        Dial {
            id: steeringDial
            height: parent.height
            value: 0.5
            onValueChanged: {
                updateData();
                //                if (!pressed) {
                //                    value = 0.5;
                //                }
            }
        }
    }

    function updateData() {
        var direction = "f";

        if (steeringDial.value > 0.55) {
            direction = "r";
        } else if (steeringDial.value < 0.45) {
            direction = "l";
        } else{
            direction = reverseSwitch.checked ? "b" : "f";
        }

        communication.updateData("h", direction, speedSlider.value * speedRangeSlider.second.value, speedSlider.value * speedRangeSlider.second.value, speedRangeSlider.second.value)
    }
}
