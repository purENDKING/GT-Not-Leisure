package com.lootgames.sudoku.sudoku;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.lootgames.sudoku.Utils.DrawHelper;
import com.lootgames.sudoku.block.SudokuTile;

import ru.timeconqueror.lootgames.api.block.tile.BoardGameMasterTile;
import ru.timeconqueror.lootgames.api.util.Pos2i;

public class SudokuRenderer extends TileEntitySpecialRenderer {

    private static final ResourceLocation BOARD = new ResourceLocation("lootgames", "textures/game/ms_board.png");

    @Override
    public void renderTileEntityAt(TileEntity teIn, double x, double y, double z, float partialTicks) {
        SudokuTile te = (SudokuTile) teIn;
        GameSudoku game = te.getGame();
        SudokuBoard board = game.getBoard();
        int size = game.getCurrentBoardSize();

        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);
        BoardGameMasterTile.prepareMatrix(te);

        bindTexture(BOARD);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glColor4f(1f, 1f, 1f, 1f);

        for (int cx = 0; cx < size; cx++) {
            for (int cz = 0; cz < size; cz++) {
                ru.timeconqueror.timecore.api.util.client.DrawHelper
                    .drawTexturedRectByParts(cx, cz, 1, 1, -0.005f, 0, 0, 1, 1, 4);
            }
        }

        GL11.glDisable(GL11.GL_DEPTH_TEST);

        Map<Integer, Integer> numberCounts = new HashMap<>();
        for (int cx = 0; cx < size; cx++) {
            for (int cz = 0; cz < size; cz++) {
                int puzzleVal = board.getPuzzleValue(cx, cz);
                int playerVal = board.getPlayerValue(new Pos2i(cx, cz));
                if (puzzleVal != 0) {
                    numberCounts.put(puzzleVal, numberCounts.getOrDefault(puzzleVal, 0) + 1);
                } else if (playerVal != 0) {
                    numberCounts.put(playerVal, numberCounts.getOrDefault(playerVal, 0) + 1);
                }
            }
        }

        Set<Pos2i> duplicatePositions = new HashSet<>();
        for (int row = 0; row < size; row++) {
            Map<Integer, Set<Pos2i>> rowMap = new HashMap<>();
            for (int col = 0; col < size; col++) {
                int puzzleVal = board.getPuzzleValue(row, col);
                int playerVal = board.getPlayerValue(new Pos2i(row, col));
                int actualVal = puzzleVal != 0 ? puzzleVal : playerVal;
                if (actualVal != 0) {
                    rowMap.computeIfAbsent(actualVal, k -> new HashSet<>())
                        .add(new Pos2i(row, col));
                }
            }
            for (Set<Pos2i> positions : rowMap.values()) {
                if (positions.size() > 1) {
                    duplicatePositions.addAll(positions);
                }
            }
        }
        for (int col = 0; col < size; col++) {
            Map<Integer, Set<Pos2i>> colMap = new HashMap<>();
            for (int row = 0; row < size; row++) {
                int puzzleVal = board.getPuzzleValue(row, col);
                int playerVal = board.getPlayerValue(new Pos2i(row, col));
                int actualVal = puzzleVal != 0 ? puzzleVal : playerVal;
                if (actualVal != 0) {
                    colMap.computeIfAbsent(actualVal, k -> new HashSet<>())
                        .add(new Pos2i(row, col));
                }
            }
            for (Set<Pos2i> positions : colMap.values()) {
                if (positions.size() > 1) {
                    duplicatePositions.addAll(positions);
                }
            }
        }

        for (int cx = 0; cx < size; cx++) {
            for (int cz = 0; cz < size; cz++) {
                Pos2i pos = new Pos2i(cx, cz);
                int puzzleVal = board.getPuzzleValue(cx, cz);
                int playerVal = board.getPlayerValue(pos);

                int actualVal = puzzleVal != 0 ? puzzleVal : playerVal;
                if (actualVal != 0) {
                    String text = Integer.toString(actualVal);
                    int count = numberCounts.getOrDefault(actualVal, 0);
                    int color;

                    if (count > 9) {
                        color = 0xFFAAAA;
                    } else if (duplicatePositions.contains(pos)) {
                        color = 0xFFFF00;
                    } else if (count == 9) {
                        color = 0x00FF00;
                    } else {
                        color = puzzleVal != 0 ? 0x808080 : 0xFFFFFF;
                    }

                    GL11.glPushMatrix();
                    GL11.glEnable(GL11.GL_DEPTH_TEST);
                    GL11.glScalef(0.1f, 0.1f, 0.1f);
                    GL11.glTranslatef((cx + 0.33f) * 10f, (cz) * 10f, -0.2f);
                    DrawHelper.drawString(text, 0, 0, 0, color, true);
                    GL11.glDisable(GL11.GL_DEPTH_TEST);
                    GL11.glPopMatrix();
                }
            }
        }

        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();

        SudokuOverlayHandler.addSupportedMaster(te.getBlockPos(), game);
    }
}
