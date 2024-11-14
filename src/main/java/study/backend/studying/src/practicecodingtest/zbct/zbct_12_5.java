package study.backend.studying.src.practicecodingtest.zbct;

public class zbct_12_5 {
    /**
     * 입력된 2D 이미지 배열에 평균 필터를 적용합니다.
     * 각 픽셀 값은 주어진 커널 크기 내 이웃들의 평균 값으로 대체됩니다.
     *
     * @param pixels 입력 이미지(픽셀 값들로 구성된 2D 배열)
     * @param kernelSize 평균을 구할 커널의 크기 (항상 홀수여야 함)
     * @return 평균 필터가 적용된 결과 이미지 (2D 배열)
     */
    public int[][] solution(int[][] pixels, int kernelSize) {
        int numRows = pixels.length; // 입력 이미지의 행 수
        int numCols = pixels[0].length; // 입력 이미지의 열 수
        int border = (kernelSize - 1) / 2; // 중앙 픽셀 주변으로 확장할 픽셀 수

        // 결과를 저장할 배열 생성
        int[][] smoothedPixels = new int[numRows][numCols];

        // 원본 이미지의 각 픽셀을 반복
        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numCols; c++) {
                int pixelSum = 0; // 커널 내 픽셀 값의 합을 저장할 변수
                int pixelCount = 0; // 유효한 픽셀 수를 카운트할 변수

                // 현재 픽셀을 중심으로 커널을 반복
                for (int neighborRow = r - border; neighborRow <= r + border; neighborRow++) {
                    for (int neighborCol = c - border; neighborCol <= c + border; neighborCol++) {
                        // 이웃 픽셀이 이미지 범위 내에 있는지 확인
                        if (neighborRow >= 0 && neighborRow < numRows && neighborCol >= 0 && neighborCol < numCols) {
                            pixelSum += pixels[neighborRow][neighborCol]; // 이웃 픽셀 값을 합에 더함
                            pixelCount++; // 유효한 픽셀 수 증가
                        }
                    }
                }

                // 평균 값을 계산하고 결과 배열에 할당
                smoothedPixels[r][c] = pixelSum / pixelCount; // 평균 값은 합을 유효한 픽셀 수로 나눈 값 (정수 나눗셈)
            }
        }
        return smoothedPixels; // 필터링된 이미지를 반환
    }
}