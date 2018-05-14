
package javadiptraceasciilib;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * The layers.
 */
 enum PlacementLayer {
    //CHECKSTYLE.OFF: JavadocVariable - Self explaining enums
    NO_LAYER(-1, -1, DiptracePCBSide.UNKNOWN),
    TOP_SILK(0, 1, DiptracePCBSide.TOP),
    TOP_ASSY(1, 0, DiptracePCBSide.TOP),
    TOP_MASK(2, 6, DiptracePCBSide.TOP),
    TOP_PASTE(3, 7, DiptracePCBSide.TOP),
    BOTTOM_PASTE(4, 8, DiptracePCBSide.BOTTOM),
    BOTTOM_MASK(5, 9, DiptracePCBSide.BOTTOM),
    BOTTOM_ASSY(6, 5, DiptracePCBSide.BOTTOM),
    BOTTOM_SILK(7, 4, DiptracePCBSide.BOTTOM),
    SIGNAL_PLANE(8, 3, DiptracePCBSide.UNKNOWN),
    ROUTE_KEEPOUT(9, 2, DiptracePCBSide.UNKNOWN),
    PLACE_KEEPOUT(10, 11, DiptracePCBSide.UNKNOWN),
//    BOTTOM_SIGNAL(11, ),
    BOARD_CUTOUT(12, 10, DiptracePCBSide.BOTH),
    USER_NON_SIGNAL_LAYER(-901, 15, DiptracePCBSide.UNKNOWN);
    //CHECKSTYLE.ON: JavadocVariable - Self explaining enums
    
    /**
     * A map of the layers and their attribute number.
     */
    private static final Map<Integer, PlacementLayer>
        PLACEMENT_LAYER_MAP_ATTRIBUTES = new HashMap<>();
    
    /**
     * A map of the markings and their item number.
     */
    private static final Map<Integer, PlacementLayer>
        PLACEMENT_LAYER_MAP_ITEMS = new HashMap<>();
    
    static {
        for (PlacementLayer layerNo : EnumSet.allOf(PlacementLayer.class)) {
            PLACEMENT_LAYER_MAP_ATTRIBUTES.put(layerNo.fAttrNo, layerNo);
            PLACEMENT_LAYER_MAP_ITEMS.put(layerNo.fItemNo, layerNo);
        }
    }

    /**
     * Get the layer by the attribute number.
     * @param layerAttrNo the layer number
     * @return the layer
     */
    static PlacementLayer getTypeByAttrNo(final int layerAttrNo) {
        return PLACEMENT_LAYER_MAP_ATTRIBUTES.get(layerAttrNo);
    }

    /**
     * Get the layer by the item number.
     * @param layerItemNo the layer number
     * @return the layer
     */
    static PlacementLayer getTypeByItemNo(final int layerItemNo) {
        return PLACEMENT_LAYER_MAP_ITEMS.get(layerItemNo);
    }
    
    /**
     * The layer attribute number.
     */
    private final int fAttrNo;
    
    /**
     * The layer item number.
     */
    private final int fItemNo;
    
    /**
     * The side of the PCB there this shape will be.
     */
    private final DiptracePCBSide fSide;

    /**
     * Initialize a PlacementLayer object.
     * @param attrNo the attribute number
     * @param itemNo the item number
     * @param side the side that this layer is on
     */
    PlacementLayer(
        final int attrNo,
        final int itemNo,
        final DiptracePCBSide side) {
        
        fAttrNo = attrNo;
        fItemNo = itemNo;
        fSide = side;
    }

    /**
     * Get the attribute number.
     * @return the number
     */
    int getAttrNo() {
        return fAttrNo;
    }

    /**
     * Get the item number.
     * @return the number
     */
    int getItemNo() {
        return fItemNo;
    }

    /**
     * Get the side of the PCB.
     * @return the side
     */
    DiptracePCBSide getSide() {
        return fSide;
    }

}
