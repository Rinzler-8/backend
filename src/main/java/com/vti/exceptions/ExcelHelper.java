package com.vti.exceptions;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.vti.entity.Category;
import com.vti.entity.Product;
import com.vti.security.service.ICategoryService;

public class ExcelHelper {

	public String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	String[] HEADERs = { "product_id", "name", "price", "product_info", "product_detail", "rating_star",
			"product_image_name", "category_id", "stock_qty" };
	String SHEET = "Sheet1";

	public boolean hasExcelFormat(MultipartFile file) {

		if (!TYPE.equals(file.getContentType())) {
			return false;
		}

		return true;
	}

	public List<Product> excelToProducts(InputStream is, ICategoryService categoryService) {
		try {
			Workbook workbook = new XSSFWorkbook(is);

			Sheet sheet = workbook.getSheet(SHEET);
			Iterator<Row> rows = sheet.iterator();

			List<Product> products = new ArrayList<Product>();

			int rowNumber = 0;
			while (rows.hasNext()) {
				Row currentRow = rows.next();

				// skip header
				if (rowNumber == 0) {
					rowNumber++;
					continue;
				}

				Iterator<Cell> cellsInRow = currentRow.iterator();

				Product product = new Product();

				int cellIdx = 0;
				while (cellsInRow.hasNext()) {
					Cell currentCell = cellsInRow.next();

					switch (cellIdx) {
					case 0:
						product.setProductId((int) currentCell.getNumericCellValue());
						break;

					case 1:
						product.setName(currentCell.getStringCellValue());
						break;

					case 2:
						product.setPrice((double) currentCell.getNumericCellValue());
						break;

					case 3:
						product.setInfo(currentCell.getStringCellValue());
						break;
					case 4:
						product.setDetail(currentCell.getStringCellValue());
						break;
					case 5:
						product.setRatingStar((short) currentCell.getNumericCellValue());
						break;
					case 6:
						product.setImageName(currentCell.getStringCellValue());
						break;
					case 7:
						int categoryId = (int) currentCell.getNumericCellValue();
						Category categoryDB = categoryService.getCategoryByID(categoryId);
						System.out.println("current " + categoryService.getCategoryByID(categoryId));
						// the Category class
						product.setCategory(categoryDB);
						break;
					case 8:
						product.setStockQty((int) currentCell.getNumericCellValue());
						break;
					default:
						break;
					}

					cellIdx++;
				}

				products.add(product);
			}

			workbook.close();

			return products;
		} catch (IOException e) {
			throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
		}
	}

	public ByteArrayInputStream productsToExcel(List<Product> products) {

		try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
			Sheet sheet = workbook.createSheet(SHEET);

			// Header
			Row headerRow = sheet.createRow(0);

			for (int col = 0; col < HEADERs.length; col++) {
				Cell cell = headerRow.createCell(col);
				cell.setCellValue(HEADERs[col]);
			}

			int rowIdx = 1;
			for (Product product : products) {
				Row row = sheet.createRow(rowIdx++);

				row.createCell(0).setCellValue(product.getProductId());
				row.createCell(1).setCellValue(product.getName());
				row.createCell(2).setCellValue(product.getPrice());
				row.createCell(3).setCellValue(product.getInfo());
				row.createCell(4).setCellValue(product.getDetail());
				row.createCell(5).setCellValue(product.getRatingStar());
				row.createCell(6).setCellValue(product.getImageName());
				row.createCell(7).setCellValue(product.getCategory().getId());
				row.createCell(8).setCellValue(product.getStockQty());
			}

			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		} catch (IOException e) {
			throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
		}
	}
}